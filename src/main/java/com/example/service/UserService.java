public interface UserService {

    UserDto createUser(SignupRequest signupRequest);

    UserDto getUserById(Long userId);

    UserDto updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);

    UserDto findByEmail(String email);

}
