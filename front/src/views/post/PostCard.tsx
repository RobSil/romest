import {FC} from "react";
import {ComplexPostDto} from "../../services/requests/PostRequest";
import {Avatar, Box, ChakraComponent, ChakraProps, Image, Text} from "@chakra-ui/react";
import {useNavigate} from "react-router-dom";

export interface PostCardProps {
    post: ComplexPostDto,
}

const PostCard: FC<PostCardProps & ChakraProps> = (props) => {

    const navigate = useNavigate()

    return (
        <Box display={"flex"} flexDirection={"column"} style={{cursor: props.cursor as string}} onClick={event => {navigate("/post/" + props.post.id)}}>
            <Image src={props.post.photo.imageKitUrl} width={"sm"} height={"sm"} mb={2}/>
            <Box display={"flex"} flexDirection={"row"} alignItems={"center"}>
                <Avatar src={props.post.author.avatar.imageKitUrl} mr={2}/>
                <Text>Author: {props.post.author.username}</Text>
            </Box>
        </Box>
    )
}

export default PostCard
