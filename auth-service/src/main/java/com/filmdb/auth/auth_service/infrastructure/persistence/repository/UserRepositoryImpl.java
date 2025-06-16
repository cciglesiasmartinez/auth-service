package com.filmdb.auth.auth_service.infrastructure.persistence.repository;

import com.filmdb.auth.auth_service.domain.model.Email;
import com.filmdb.auth.auth_service.domain.model.UserId;
import com.filmdb.auth.auth_service.domain.model.Username;
import com.filmdb.auth.auth_service.infrastructure.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements SpringDataUserRepository {

    private final SpringDataUserRepository springData;

    @Override
    public Optional<UserEntity> findById(UserId id) {
        return springData.findById(id.value());
    }

    @Override
    public Optional<UserEntity> findByEmail(Email email) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findByUsername(Username username) {
        return Optional.empty();
    }

    @Override
    public void delete(UserEntity user) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsByEmailOrUsername(String email, String username) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UserEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<UserEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserEntity getOne(String s) {
        return null;
    }

    @Override
    public UserEntity getById(String s) {
        return null;
    }

    @Override
    public UserEntity getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends UserEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UserEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends UserEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<UserEntity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<UserEntity> findAll() {
        return List.of();
    }

    @Override
    public List<UserEntity> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public List<UserEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return null;
    }
}
