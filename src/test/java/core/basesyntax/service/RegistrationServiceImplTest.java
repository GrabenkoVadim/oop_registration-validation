package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private StorageDao storage;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        Storage.people.clear();
        user = new User();
        user.setLogin("evfrosina");
        user.setPassword("fronyaa");
        user.setAge(26);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Negative_Age_NotOk() {
        user.setAge(-15);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Child_NotOk() {
        user.setAge(3);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Short_Password_NotOk() {
        user.setPassword("gaeww");
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Long_Password_NotOk() {
        user.setPassword("1234hdfhrey45yhdfhbcfhrthdhrdfhdfh56");
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    @Test
    void register_Short_Login_NotOk() {
        user.setLogin("gewew");
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    @Test
    void register_Long_Login_NotOk() {
        user.setLogin("1234hdfhredhfsdrhbv cx3465246346y");
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    @Test
    void register_nullUser_NotOk() {
        user = null;
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_With_same_Login_NotOk() throws RegistrationException {
        user.setLogin("Semenenko");
        user.setAge(25);
        user.setPassword("54723532");
        User user2 = new User();
        user2.setLogin("Semenenko");
        user2.setAge(19);
        user2.setPassword("ncgfncnngc");
        registrationService.register(user2);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    @Test
    void register_ok() throws RegistrationException {
        User expected = user;
        expected.setLogin("frozinonovic");
        User actual = registrationService.register(expected);
        Assertions.assertEquals(expected, actual);
    }
}