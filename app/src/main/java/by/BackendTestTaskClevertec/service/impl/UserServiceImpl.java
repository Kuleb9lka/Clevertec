package by.BackendTestTaskClevertec.service.impl;

import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.repository.UserRepository;
import by.BackendTestTaskClevertec.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User getById(long id) {

        return repository.getById(id);
    }

    @Override
    public User create(User user) {

        return repository.create(user);
    }

    @Override
    public void update(User user) {

        repository.update(user);
    }

    @Override
    public void delete(long id) {

        repository.delete(id);
    }
}
