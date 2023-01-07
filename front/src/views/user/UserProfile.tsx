import {FC, useEffect, useState} from "react";
import BoardService from "../../services/BoardService";
import {useParams} from "react-router-dom";
import {SimpleBoardDto} from "../../services/requests/BoardRequests";
import {Link as RouterLink} from "react-router-dom";
import {Link} from "@chakra-ui/react";


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
            {boards.map((board) => {
                return (
                    <Link as={RouterLink} to={`/${username}/${board.minimizedName}`}>{board.name}</Link>
                )
            })}
        </>
    )
}

export default UserProfile
