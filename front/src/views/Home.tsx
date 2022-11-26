import {FC, useEffect} from "react";
import {useAppDispatch, useAppSelector} from "../redux/reduxUtil";
import {useNavigate} from "react-router-dom";
import {Button, useDisclosure} from "@chakra-ui/react";
import AuthService from "../services/AuthService";
import {logout} from "../redux/reducers/ActionCreators";
import {getUserData} from "../redux/reducers/UserUtil";
import BoardCreateModal from "./modals/BoardCreateModal";

const Home: FC = () => {

    const navigate = useNavigate()
    const { isOpen, onOpen, onClose } = useDisclosure()

    let state = useAppSelector(state => state.userReducer)
    const dispatch = useAppDispatch()

    let userData = getUserData()

    useEffect(() => {
        // if (getUserData() == null) {
        //     navigate("/login")
        // }
    })

    const processLogout = () => {
        dispatch(logout())
    }

    return (
        <div>
            <Button onClick={() => processLogout()}>Log out</Button>
            <h1>Just an empty header for now.</h1>
            <h1>Hello, {userData?.username}</h1>
            <Button onClick={onOpen}>Create board</Button>

            <BoardCreateModal isOpen={isOpen} onClose={onClose} />
        </div>
    )
}

export default Home