package com.sheffieldtrains.db;

import com.sheffieldtrains.domain.user.Address;
import com.sheffieldtrains.domain.user.BankDetail;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.domain.user.UserRole;
import com.sheffieldtrains.service.EmailInUseException;
import com.sheffieldtrains.service.UnknownUserException;
import com.sheffieldtrains.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepository extends Repository {


    private static final String ADD_USER_ROLE_SQL = """
            INSERT INTO team066.UserInRole (userID, roleName)
            VALUES  (?, ?)
            AS newUserRole
            ON DUPLICATE KEY UPDATE 
            userID=newUserRole.userID, 
            roleName=newUserRole.roleName
""";
    private static final String REMOVE_ROLE_SQL = """
            DELETE FROM team066.UserInrole 
            WHERE userID=? and roleName=?
            """;
    private static String GET_USER_BY_EMAIL_OR_USER_ID_SQL = """
        SELECT u.userID, email , password, forename, surname, a.houseNumber, a.postcode, a.roadName, a.cityName, ur.roleName
        FROM `team066`.`User` u
        JOIN `team066`.`Address` a ON u.houseNumber=a.houseNumber AND a.postcode=u.postcode
        LEFT JOIN `team066`.`UserInRole` ur   ON u.userID=ur.userID
        WHERE u.email=? or u.userID=?
            """;

    private static String GET_ALL_USER_SQL = """
        SELECT u.userID, email , password, forename, surname, a.houseNumber, a.postcode, a.roadName, a.cityName, ur.roleName
        FROM `team066`.`User` u
        JOIN `team066`.`Address` a ON u.houseNumber=a.houseNumber AND a.postcode=u.postcode
        LEFT JOIN `team066`.`UserInRole` ur   ON u.userID=ur.userID
            """;

    private static String REGISTER_NEW_USER_SQL = """
        INSERT INTO `team066`.`User` (`email`, `password`, `forename`, `surname`, `houseNumber`, `postcode`) 
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    private static String MODIFY_USER_SQL = """
        UPDATE `team066`.`User` 
        SET email=?, 
        password=?,
        forename=?,
        surname=?,
        houseNumber=?,
        postcode=?
        WHERE userID=?
        """;

    private static String REGISTER_OR_CHANGE_ADDRESS_SQL = """
            INSERT INTO `team066`.`Address` (`houseNumber`, `roadName`, `cityName`, `postcode`) 
            VALUES (?, ?, ?, ?)
            AS newAddress
            ON DUPLICATE KEY UPDATE 
            houseNumber=newAddress.houseNumber,
            roadName=newAddress.roadName,
            cityName=newAddress.cityName,
            postcode=newAddress.postcode
            """;

    private static String REGISTER_OR_CHANGE_BANK_DETAILS_SQL= """
            INSERT INTO `team066`.`BankDetail` (`bankCardNumber`, `bankCardName`, `cardHolderName`, `cardExpiryDate`, `securityCode`, `userID`) 
            VALUES (?, ?, ?, ?, ?, ?)
            AS newCard
            ON DUPLICATE KEY UPDATE
            bankCardNumber=newCard.bankCardNumber,
            bankCardName=newCard.bankCardName,
            cardHolderName=newCard.cardHolderName,
            cardExpiryDate=newCard.cardExpiryDate,
            securityCode=newCard.securityCode,
            userID=newCard.userID
            """;

    private static final String GET_BANK_DETAIL_SQL = """
        SELECT  bankCardNumber,
                bankCardName,
                cardHolderName,
                cardExpiryDate,
                securityCode
        FROM team066.BankDetail
        WHERE userID=?
""";
    public static User registerUser(String email,
                                    String password,
                                    String forename,
                                    String surname,
                                    String houseNumber,
                                    String roadName,
                                    String cityName,
                                    String postcode) {

        User user=new User(email, password, forename, surname);
        PreparedStatement addressStmt=null;
        PreparedStatement userStmt=null;
        Connection connection=null;
        try {
            if(isUserInExistence(email, null)){
                throw new UserAlreadyExistException("An user with the email id already exists, please login or use a different email to sign up");
            };
            //need to save both Address and User in one transaction.
            connection=getConnection();
            connection.setAutoCommit(false);
             /*addressStmt= connection.prepareStatement(REGISTER_OR_CHANGE_ADDRESS_SQL);
             addressStmt.setString(1, houseNumber );
             addressStmt.setString(2, roadName);
             addressStmt.setString(3, cityName);
             addressStmt.setString(4, postcode);
             addressStmt.executeUpdate();*/
            Address address= createOrModifyAddress(connection, houseNumber, roadName, cityName, postcode);
            user.setAddress(address);

            userStmt = connection.prepareStatement(REGISTER_NEW_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, email);
            userStmt.setString(2, password);
            userStmt.setString(3, forename);
            userStmt.setString(4, surname);
            userStmt.setString(5, houseNumber);
            userStmt.setString(6, postcode);
            int affectedRows =userStmt.executeUpdate();
            connection.commit();
            if (affectedRows>0) {
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                       /* Integer generatedUserId = generatedKeys.getInt("userID");*/
                        Integer generatedUserId = generatedKeys.getInt(1);
                        user.setUserID(generatedUserId);
                    }
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException("User registration failed: Database error.  Please try again later");
        }
        finally {
                try {
                   if (addressStmt!=null) {
                       addressStmt.close();
                   }
                    if (userStmt!=null) {
                        userStmt.close();
                    }
                    if (connection!=null) {
                        connection.close();
                    }
                }
                catch (SQLException ex){
                    throw new RuntimeException("Error encountered when trying to close db statements");
                }
        }
        return user;
    }

    private static Address createOrModifyAddress(Connection connection,
                                  String houseNumber,
                                  String roadName,
                                  String cityName,
                                  String postcode) throws SQLException{
        Address address=new Address(houseNumber, roadName, cityName, postcode);
        PreparedStatement addressStmt= connection.prepareStatement(REGISTER_OR_CHANGE_ADDRESS_SQL);
        addressStmt.setString(1, houseNumber );
        addressStmt.setString(2, roadName);
        addressStmt.setString(3, cityName);
        addressStmt.setString(4, postcode);
        addressStmt.executeUpdate();
        if (addressStmt!=null){
            addressStmt.close();
        }
        return address;
    }

    //check if the user already in existence, use email or userID.
    private static boolean isUserInExistence(String email, Integer userID) {
        boolean result=false;
        if (email==null && userID==null){
            throw new IllegalArgumentException("Cannot check user existence. Either email or userID will be needed");
        }
        ResultSet resultSet=null;
        try (PreparedStatement stmt=getConnection().prepareStatement(GET_USER_BY_EMAIL_OR_USER_ID_SQL)) {
            if (email!=null) {
                stmt.setString(1, email);
            }
            else {
                stmt.setNull(1, Types.VARCHAR);
            }
            if (userID!=null){
                stmt.setInt(2, userID);
            }
            else {
                stmt.setNull(2, Types.INTEGER);
            }

            resultSet = stmt.executeQuery();
            if (resultSet.next()){
                String retrievedEmail = resultSet.getString("email");
                if (retrievedEmail!=null) {
                    result =true;
                }
            }
            return result;
            }
        catch (SQLException ex) {
            throw new RuntimeException("Unable to register user: Database error encoutered");
        }
        finally {
                try {
                    if(resultSet!=null) {
                        resultSet.close();
                    }
                }
                catch (SQLException ex){
                    throw new RuntimeException("Unable to close db resources when registering user.");
                }
        }
    }

    public static User getUser(String email) {
        if(email==null){
            throw new UnknownUserException("Cannot identify user: user email is not provided");
        }
        ResultSet resultSet = null;
        User user =null;
        try (PreparedStatement stmt = getConnection().prepareStatement(GET_USER_BY_EMAIL_OR_USER_ID_SQL)) {
            stmt.setString(1, email);
            stmt.setNull(2, Types.INTEGER);
            resultSet = stmt.executeQuery();
            List<User> users= buildUsers(resultSet);
            if(!users.isEmpty()){
                user=users.get(0);
            }
           /* while (resultSet.next()) {
                user= buildUsers(resultSet).get(0);
            }*/
        } catch (SQLException ex) {
            throw new RuntimeException("Database error when trying to retrieve user information");
        }
        return user;
    }

    private static List<User> buildUsers(ResultSet resultSet) throws SQLException{
        List<User> users=new ArrayList<>();
        long currentUserId=-1;
        User user =null;
        while (resultSet.next()) {
            int userId=resultSet.getInt("userID");
            if (userId!=currentUserId) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String forename = resultSet.getString("forename");
                String surname = resultSet.getString("surname");
                String houseNumber = resultSet.getString("houseNumber");
                String roadName = resultSet.getString("roadName");
                String cityName = resultSet.getString("cityName");
                String postcode = resultSet.getString("postcode");
                user = new User(email, password, forename, surname);
                user.setUserID(userId);
                Address address = new Address(houseNumber, roadName, cityName, postcode);
                user.setAddress(address);
                BankDetail bankDetail=getBankDetail(user.getUserID());
                user.setBankDetail(bankDetail);
                users.add(user);
                currentUserId=userId;
            } //next build roles
            String roleName=resultSet.getString("roleName");
            if (roleName!=null) {
                user.addRole(UserRole.valueOf(roleName));
            }
        }
        return users;
    }


    public static List<User> getAllUser() {
        ResultSet resultSet = null;
        List<User> users=new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(GET_ALL_USER_SQL)) {
            resultSet = stmt.executeQuery();
            users= buildUsers(resultSet);
        } catch (SQLException ex) {
            throw new RuntimeException("Database error when trying to retrieve user information");
        }
        return users;
    }

    public static void addUserRole(Integer userId, UserRole roleName) {
        try (PreparedStatement roleStmt=getConnection().prepareStatement(ADD_USER_ROLE_SQL)){
            roleStmt.setInt(1, userId);
            roleStmt.setString(2, roleName.toString());
            roleStmt.executeUpdate();
        }
        catch (SQLException ex){
            throw new RuntimeException("Database error encountered when trying to add user role");
        }
    }

    public static User modifyUserDetails(User user) {
//        PreparedStatement addressStmt=null;
        PreparedStatement userStmt=null;
        Connection connection=null;
        try {
            if(!isUserInExistence(null, user.getUserID())) {
                throw new UnknownUserException("Can not find the user");
            };
            if (newEmailBelongToOtherUserAlready(user)){
                throw new EmailInUseException();
            }
            //need to save both Address and User in one transaction.
            connection=getConnection();
            connection.setAutoCommit(false);
             /*addressStmt= connection.prepareStatement(REGISTER_OR_CHANGE_ADDRESS_SQL);
             addressStmt.setString(1, houseNumber );
             addressStmt.setString(2, roadName);
             addressStmt.setString(3, cityName);
             addressStmt.setString(4, postcode);
             addressStmt.executeUpdate();*/
            Address address =user.getAddress();
            createOrModifyAddress(connection,
                                  address.getHouseNumber(),
                                  address.getRoadName(),
                                  address.getCityName(),
                                  address.getPostcode());
            /*user.setAddress(address);*/
            /*UPDATE `team066`.`user`
            SET email=?,
            password=?,
            forename=?,
            surname=?,
            houseNumber=?,
            postcode=?
            WHERE userID=?*/
            userStmt = connection.prepareStatement(MODIFY_USER_SQL);
            userStmt.setString(1, user.getEmail());
            userStmt.setString(2, user.getPassword());
            userStmt.setString(3, user.getForename());
            userStmt.setString(4, user.getSurname());
            userStmt.setString(5, address.getHouseNumber());
            userStmt.setString(6, address.getPostcode());
            userStmt.setInt(7, user.getUserID());
            userStmt.executeUpdate();
            connection.commit();
        }
        catch (SQLException ex) {
            throw new RuntimeException("Failed to change user details.");
        }
        finally {
            try {
                if (userStmt!=null) {
                    userStmt.close();
                }
                if (connection!=null) {
                    connection.close();
                }
            }
            catch (SQLException ex){
                throw new RuntimeException("Error encountered when trying to close db statements");
            }
        }
        return user;
    }

    private static boolean newEmailBelongToOtherUserAlready(User user) {
        boolean result=false;
        User retrievedUser=UserService.getUser(user.getEmail());
        if (retrievedUser!=null){
            result= (retrievedUser.getUserId() !=user.getUserId());
        }
        return result;
    }

    public static void addOrModifyBankDetails(Integer userID,
                                              String bankCardName,
                                              String cardHolderName,
                                              String bankCardNumber,
                                              Date cardExpiryDate,
                                              int securityCode) {
        if (!isUserInExistence(null, userID)){
            throw new UnknownUserException("Cannot add bank details to an unregistered user");
        }
        try(PreparedStatement stmt = getConnection().prepareStatement(REGISTER_OR_CHANGE_BANK_DETAILS_SQL)){
            /* bankCardNumber int          ,
            bankCardName   varchar(50)  ,
            cardHolderName varchar(100) ,
            cardExpiryDate date         ,
            securityCode   int          ,
            userID         int          ,*/
            stmt.setString(1, bankCardNumber);
            stmt.setString(2, bankCardName);
            stmt.setString(3, cardHolderName);
            stmt.setDate(4, (java.sql.Date) cardExpiryDate);
            stmt.setInt(5, securityCode);
            stmt.setInt(6, userID);
            stmt.executeUpdate();
        }
        catch (SQLException ex){
            throw new RuntimeException("Cannot add new bank details");
        }
    }


    public static BankDetail getBankDetail(Integer userId) {
        /*PreparedStatement bankStmt=null;*/
        Connection connection=null;
        BankDetail bankDetail=null;
        try (PreparedStatement bankStmt=getConnection().prepareStatement(GET_BANK_DETAIL_SQL)) {
            bankStmt.setLong(1, userId);
            ResultSet resultSet=bankStmt.executeQuery();
            if (resultSet.next()) {
                String bankCardNumber = resultSet.getString("bankCardNumber");
                String bankCardName = resultSet.getString("bankCardName");
                String cardHolderName = resultSet.getString("cardHolderName");
                Date cardExpiryDate = resultSet.getDate("cardExpiryDate");
                int securityCode = resultSet.getInt("securityCode");
                bankDetail = new BankDetail(bankCardName, cardHolderName, bankCardNumber, cardExpiryDate, securityCode);
            }
        }
        catch (SQLException ex){
            throw new RuntimeException("Database error when trying to retrieve bank datails");
        }
        return bankDetail;
    }

    public static void demoteUser(User user) {
        try (PreparedStatement removeStmt=getConnection().prepareStatement(REMOVE_ROLE_SQL)){
            removeStmt.setInt(1, user.getUserID());
            removeStmt.setString(2, UserRole.STAFF.toString());
            removeStmt.executeUpdate();
        }
        catch (SQLException ex){
            throw new RuntimeException("Database error encountered when trying to demote user");
        }
    }

    public static void deleteUser(Integer userID) {
        String deleteUserRoleSql="delete from team066.UserInRole where userID =?";
        String deleteUserBankDetailsSql="delete from team066.BankDetail where userID =?";
        String deleteUserSql="delete from team066.User where userID=?";
        String deleteAddressSql="delete from team066.Address where houseNumber=? and postcode=?";
        try (PreparedStatement userRoleStmt=getConnection().prepareStatement(deleteUserRoleSql);
             PreparedStatement bankStmt=getConnection().prepareStatement(deleteUserBankDetailsSql);
             PreparedStatement userStmt=getConnection().prepareStatement(deleteUserSql);
             /*PreparedStatement addressStmt=getConnection().prepareStatement(deleteAddressSql);*/
            ){
            userRoleStmt.setInt(1, userID);
            userRoleStmt.executeUpdate();
            bankStmt.setInt(1, userID);
            bankStmt.executeUpdate();
            /*addressStmt.executeUpdate();*/
            userStmt.setInt(1, userID);
            userStmt.executeUpdate();
        }
        catch (SQLException ex){
            new RuntimeException("Error encountered when trying to delete user informaion");
        }
    }
}
