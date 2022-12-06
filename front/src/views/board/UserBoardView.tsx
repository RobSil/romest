import {FC, useEffect, useState} from "react";
import BoardService from "../../services/BoardService";
import {useParams} from "react-router-dom";
import PostService from "../../services/PostService";
import {SimpleBoardDto} from "../../services/requests/BoardRequests";
import {ComplexPostDto, ComplexPostPageableDto} from "../../services/requests/PostRequest";


const UserBoardView: FC = () => {

    const {username, minimizedName} = useParams()

    const [board, setBoard] = useState<SimpleBoardDto | null>(null)
    const [posts, setPosts] = useState<ComplexPostPageableDto | null>(null)

    const [pageNumber, setPageNumber] = useState(0)
    const [pageSize, setPageSize] = useState(20)

    useEffect(() => {
        BoardService.getByUsernameAndMinimizedName(username!, minimizedName!)
            .then(req => {
                if (req.request.status === 200) {
                    setBoard(req.data)
                }
            })
        PostService.getByUsernameAndBoardMinimizedName(username!, minimizedName!, pageNumber, pageSize)
            .then(req => {
                if (req.request.status === 200) {
                    setPosts(req.data)
                }
            })
    }, [])

    return (
        <></>
    )
}

export default UserBoardView
