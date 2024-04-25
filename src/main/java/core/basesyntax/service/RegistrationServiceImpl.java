package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import static core.basesyntax.db.Storage.people;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private static final int MAXIMUM_USERNAME_AND_LOGIN_LENGTH = 25;
    private static final int MAXIMUM_AGE = 150;

    private final StorageDao storageDao = new StorageDaoImpl();

    public boolean UserCorrectInput(@org.jetbrains.annotations.NotNull User user) throws RegistrationException {
        if (user.getLogin() == null || user.getLogin().length() < MINIMUM_LOGIN_LENGTH || user.getLogin().length() > MAXIMUM_USERNAME_AND_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be between 6 and 25 characters");
        } else if (user.getPassword() == null || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH || user.getPassword().length() > MAXIMUM_USERNAME_AND_LOGIN_LENGTH) {
            throw new RegistrationException("Password must be between 6 and 25 characters");
        } else if (user.getAge() == null) {
            throw new RegistrationException("Age must be greater than or equal to 18");
        }
        return true;
    }

    @Override
    public User register(User user) throws RegistrationException {
        if (!people.isEmpty()) {
            for (User person : people) {
                if (user != null) {
                    if (person.getLogin().equals(user.getLogin())) {
                        throw new RegistrationException("such login already exists");
                    }
                }
            }
        }
        if (user == null || !UserCorrectInput(user) || user.getAge() < MINIMUM_AGE || user.getAge() > MAXIMUM_AGE) {
            throw new RegistrationException("Invalid login or password or a duplicate user");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        storageDao.add(user);
        return user;
    }
}
