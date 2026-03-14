package br.com.tastemanager.validator;

class UserValidatorTest {

//    private UserRepository userRepository;
//    private UserValidator userValidator;
//
//    @BeforeEach
//    void setUp() {
//        userRepository = Mockito.mock(UserRepository.class);
//        userValidator = new UserValidator(userRepository);
//    }
//
//    @Test
//    void validateLoginAvailability_ShouldNotThrow_WhenLoginIsAvailable() {
//        String login = "availableLogin";
//        when(userRepository.findIdByLogin(login)).thenReturn(Optional.empty());
//
//        assertDoesNotThrow(() -> userValidator.validateLoginAvailability(login));
//        verify(userRepository, times(1)).findIdByLogin(login);
//    }
//
//    @Test
//    void validateLoginAvailability_ShouldThrow_WhenLoginIsUnavailable() {
//        String login = "unavailableLogin";
//        when(userRepository.findIdByLogin(login)).thenReturn(Optional.of(1L));
//
//        assertThrows(IllegalArgumentException.class, () -> userValidator.validateLoginAvailability(login));
//        verify(userRepository, times(1)).findIdByLogin(login);
//    }
//
//    @Test
//    void validateUserExistsById_ShouldNotThrow_WhenUserExists() {
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
//
//        assertDoesNotThrow(() -> userValidator.validateUserExistsById(userId));
//        verify(userRepository, times(1)).findById(userId);
//    }
//
//    @Test
//    void validateUserExistsById_ShouldThrow_WhenUserDoesNotExist() {
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> userValidator.validateUserExistsById(userId));
//        verify(userRepository, times(1)).findById(userId);
//    }
//
//    @Test
//    void validateLoginAvailability_ShouldThrowException_WhenLoginIsUnavailable() {
//        String login = "unavailableLogin";
//        when(userRepository.findIdByLogin(login)).thenReturn(Optional.of(1L));
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> userValidator.validateLoginAvailability(login));
//        assertEquals("This login is unavailable. Please choose a different one.", exception.getMessage());
//        verify(userRepository, times(1)).findIdByLogin(login);
//    }
//
//    @Test
//    void validateUserExistsById_ShouldThrowException_WhenUserDoesNotExist() {
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> userValidator.validateUserExistsById(userId));
//        assertEquals("User with the given ID does not exist.", exception.getMessage());
//        verify(userRepository, times(1)).findById(userId);
//    }
}