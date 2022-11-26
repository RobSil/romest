import {ChangeEventHandler, FC, useEffect, useState} from "react";
import {
    Button,
    Center,
    FormControl,
    FormErrorMessage,
    FormLabel, Heading,
    Input,
    InputGroup,
    InputRightElement,
    useToast
} from "@chakra-ui/react";
import AuthService from "../../services/AuthService";
import $api from "../../services/axios";
import {useAppDispatch, useAppSelector} from "../../redux/reduxUtil";
import {UserState} from "../../redux/reducers/UserSlice";
import {useNavigate} from "react-router-dom";
import {login} from "../../redux/reducers/ActionCreators";
import {useSelector} from "react-redux";
import {validateEmail, validatePassword} from "./LoginUtil";
import {getUserData} from "../../redux/reducers/UserUtil";

const Login: FC = () => {

    const toast = useToast()
    let state = useAppSelector(state => state.userReducer)
    const navigate = useNavigate()
    const dispatch = useAppDispatch()

    const [showPassword, setShowPassword] = useState<boolean>(false)

    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    let isEmailError: boolean = validateEmail(email)
    let isPasswordError: boolean = validatePassword(password, 4)

    useEffect(() => {
        if (getUserData() != null) {
            navigate("/home")
        }
    }, [state])

    // useEffect(() => {
    //     if (state.authenticated) {
    //         navigate("/home")
    //     }
    // }, [state])
    //
    // useEffect(() => {
    //
    //     let interval = setInterval(() => {
    //         console.log(state)
    //     }, 3000)
    //
    //     return () => {
    //         clearInterval(interval)
    //     }
    // }, [])

    const attemptLogin = () => {

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

        if (!password || !validatePassword(password, 4)) {
            toast({
                title: 'Password is invalid.',
                description: "Make sure that your password is fine.",
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        dispatch(login(email, password))
            .then((req) => {

                switch (req.request.status) {
                    case 200:
                        toast({
                            title: 'Successfully logged in.',
                            // description: "Make sure that your password is fine.",
                            status: 'success',
                            duration: 5000,
                            isClosable: true,
                            position: "top-right",
                        })
                        break

                    case 401:
                        toast({
                            title: 'Something wrong in your credentials, check is this fine!',
                            // description: "Make sure that your password is fine.",
                            status: 'warning',
                            duration: 5000,
                            isClosable: true,
                            position: "top-right",
                        })
                        break

                    default:
                        toast({
                            title: 'Hmm.. There is something wrong on server side!',
                            description: "Get in touch with administrations and tell them your problem!",
                            status: 'warning',
                            duration: 5000,
                            isClosable: true,
                            position: "top-right",
                        })
                        break
                }
            })
    }

    return (
        <>
            <div style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                flexDirection: "column",
                marginTop: "300px"
            }}>
                {/*<h1 style={{fontSize: "xx-large", fontWeight: "bold", marginBottom: "30px"}}>Login Form</h1>*/}
                <Heading fontSize={"xx-large"} fontWeight={"bold"} marginBottom={"30px"}>Login Form</Heading>
                <form>

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
                                    validatePassword(e.target.value, 4)
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
                        <Button colorScheme="blue" onClick={() => attemptLogin()}>Log in</Button>
                    </FormControl>

                </form>

                <Button mt="30px" colorScheme="linkedin" onClick={() => navigate("/register")}>Register</Button>
            </div>
        </>
    );
};

export default Login;