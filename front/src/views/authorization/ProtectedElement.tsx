import {FC, ReactElement, ReactNode} from "react";
import AuthService from "../../services/AuthService";
import {userSlice} from "../../redux/reducers/UserSlice";
import {useAppDispatch} from "../../redux/reduxUtil";
import {useNavigate} from "react-router-dom";
import {getUserData} from "../../redux/reducers/UserUtil";
import {useToast} from "@chakra-ui/react";

interface Props {
    children: ReactElement
}

// @ts-ignore
const ProtectedElement: FC<Props> = ({children}: Props) => {

    const navigate = useNavigate()
    const dispatch = useAppDispatch()
    const toast = useToast()

    if (getUserData() == null) {
        AuthService.refresh()
            .then((req) => {
                if (req.request.status === 200) {
                    dispatch(userSlice.actions.setUserInfo(req.data))
                    return children
                } else {
                    dispatch(userSlice.actions.unauthorized("unauthorized"))
                    toast({
                        title: 'Need to login first!',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                    navigate("/login")
                }
            })
    } else {
        return children
    }

}

export default ProtectedElement