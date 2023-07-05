import {FC, useEffect, useState} from "react";
import BoardService from "../../services/BoardService";
import {useParams} from "react-router-dom";
import {SimpleBoardDto} from "../../services/requests/BoardRequests";
import {Link as RouterLink} from "react-router-dom";
import {Text, Link} from "@chakra-ui/react";


const UserProfile: FC = () => {

    const username: string | undefined = useParams().username
    const [boards, setBoards] = useState<SimpleBoardDto[]>([])

    useEffect(() => {
        if (username != undefined) {
            BoardService.getAllByUser(username)
                .then((req) => {
                    if (req.request.status === 200) {
                        setBoards(req.data)
                    }
                })
        }
    }, [])

    return (
        <>
            <Text>Boards: </Text>
            {boards.map((board) => {
                return (
                    <Link style={{ borderStyle: "solid", borderWidth: 1, borderColor: "gray", borderRadius: 100, padding: 7, margin: 3}} as={RouterLink} to={`/${username}/${board.minimizedName}`}>{board.name}</Link>
                )
            })}
        </>
    )
}

export default UserProfile
