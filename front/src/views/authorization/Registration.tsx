import {FC, useEffect, useState} from "react";
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    Input,
    InputGroup,
    InputRightElement,
    useToast
} from "@chakra-ui/react";
import {useNavigate} from "react-router-dom";
import {validateEmail, validatePassword, validateUsername} from "./LoginUtil";
import AuthService from "../../services/AuthService";
import {getUserData} from "../../redux/reducers/UserUtil";

const Registration: FC = () => {

    const toast = useToast()
    const navigate = useNavigate()

    const [showPassword, setShowPassword] = useState<boolean>(false)

    const [username, setUsername] = useState<string>("")
    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    let isEmailError: boolean = validateEmail(email)
    let isUsernameError: boolean = validateUsername(username, 2)
    let isPasswordError: boolean = validatePassword(password, 6)

    useEffect(() => {
        if (getUserData() != null) {
            navigate("/home")
        }
    })

    const attemptRegister = () => {
        if (!email || !validateEmail(email)) {
            toast({
                title: 'Email is invalid.',
                description: "Make sure that your email is fine.",
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        if (!password || !validatePassword(password, 6)) {
            toast({
                title: 'Password is invalid.',
                description: "Make sure that your password is fine.",
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        if (!username || !validateUsername(username, 2)) {
            toast({
                title: 'Username is invalid.',
                description: "Make sure that your password is fine.",
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        AuthService.register({username, email, password})
            .then((req) => {
                if (req.request.status === 201) {
                    toast({
                        title: 'Successfully registered.',
                        description: "Please login now.",
                        status: 'success',
                        duration: 5000,
                        isClosable: true,
                    })
                    navigate("/login")
                } else {
                    toast({
                        title: 'Some error occurred during registration process.',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                }
            })
    }

    return (
        <>
            <div>
                <Button onClick={() => navigate("/")}>Home</Button>
            </div>
            <div style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                flexDirection: "column",
                marginTop: "300px"
            }}>
                <h1 style={{fontSize: "xx-large", fontWeight: "bold", marginBottom: "30px"}}>Registration form</h1>
                <form>

                    <FormControl isRequired isInvalid={!isEmailError}>
                        <FormLabel>Username</FormLabel>
                        <Input placeholder="Username" onChange={(e) => {
                            setUsername(e.target.value)
                            isUsernameError = validateUsername(e.target.value, 2)
                        }}/>
                        {!isUsernameError && (<FormErrorMessage>Username is invalid.</FormErrorMessage>)}
                    </FormControl>

                    <FormControl isRequired isInvalid={!isEmailError}>
                        <FormLabel>Email</FormLabel>
                        <Input placeholder="Email" onChange={(e) => {
                            setEmail(e.target.value)
                            isEmailError = validateEmail(e.target.value)
                        }}/>
                        {!isEmailError && (<FormErrorMessage>Email is invalid.</FormErrorMessage>)}
                    </FormControl>

                    <FormControl isRequired isInvalid={!isPasswordError}>
                        <FormLabel>Password</FormLabel>
                        <InputGroup size='md'>
                            <Input
                                pr='4.5rem'
                                type={showPassword ? 'text' : 'password'}
                                placeholder='Enter password'
                                onChange={(e) => {
                                    setPassword(e.target.value)
                                    validatePassword(e.target.value, 6)
                                }}
                            />
                            <InputRightElement width='4.5rem'>
                                <Button
                                    h='1.75rem'
                                    size='sm'
                                    onClick={() => setShowPassword(!showPassword)}>
                                    {showPassword ? 'Hide' : 'Show'}
                                </Button>
                            </InputRightElement>
                        </InputGroup>
                        {!isPasswordError && (<FormErrorMessage>Password is invalid.</FormErrorMessage>)}
                    </FormControl>

                    <FormControl display="flex" justifyContent="center" alignItems="center" marginTop="30px">
                        <Button colorScheme="blue" onClick={() => attemptRegister()}>Register</Button>
                    </FormControl>

                </form>

                <Button mt="30px" colorScheme="linkedin" onClick={() => navigate("/login")}>Log in</Button>
            </div>
        </>
    )
}

export default Registration
