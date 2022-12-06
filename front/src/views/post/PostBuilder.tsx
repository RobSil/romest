import {FC, useState} from "react";
import {
    Box, BoxProps,
    Button,
    Flex,
    FormControl,
    FormLabel,
    forwardRef,
    Heading,
    Input,
    Textarea,
    InputGroup, useToast
} from "@chakra-ui/react";

const PostBuilder: FC = () => {

    const toast = useToast()

    const [title, setTitle] = useState("")
    const [text, setText] = useState("")
    const [image, setImage] = useState<File | null>(null)

    const createPost = () => {
        if (image == null) {
            toast({
                title: 'Please choose an image.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }


    }

    return (
        //     style={{
        //     display: "flex",
        //         alignItems: "center",
        //         justifyContent: "center",
        //         flexDirection: "column",
        //         marginTop: "300px"
        // }}
        //     <Box alignItems={"center"}>
        //         <form>
        //             <FormControl>
        //                 <FormLabel>Title</FormLabel>
        //                 <Input placeholder="Title" onChange={(e) => {
        //                     setTitle(e.target.value)
        //                 }}/>
        //             </FormControl>
        //         </form>
        //     </Box>
        <Flex width="full" align="center" justifyContent="center">
            <Box p={2} mt={100}>
                <Box textAlign="center">
                    <Heading>Create a Post</Heading>
                </Box>
                <Box my={4} textAlign="left">
                    <form>

                        <FormControl>
                            <FormLabel>Title</FormLabel>
                            <Input placeholder="Title" onChange={(e) => {
                                setTitle(e.target.value)
                            }}/>
                        </FormControl>

                        <FormControl mt={6}>
                            <FormLabel>Text</FormLabel>
                            <Textarea placeholder="Text"/>
                        </FormControl>

                        <FormControl isRequired mt={6}>
                            <FormLabel htmlFor="writeUpFile">File Label</FormLabel>
                            <InputGroup>
                                {/*<InputLeftElement*/}
                                {/*    pointerEvents="none"*/}
                                {/*    children={<Icon />}*/}
                                {/*/>*/}
                                {/*<Input type='file' style={{ display: 'none' }} ref={inputRef}></Input>*/}
                                <Input
                                    accept={".jpg,.jpeg,.png"}
                                    placeholder={"Pick a file" || "Your file ..."}
                                    type={"file"}
                                    onChange={(e) => {
                                        if (e.target.files != null) {
                                            if (e.target.files.length === 1) {
                                                setImage(e.target.files[0])
                                            }
                                        }
                                    }}
                                    // value={value}
                                />
                            </InputGroup>
                        </FormControl>
                        <Button width="full" mt={4} onClick={() => createPost()}>
                            Create
                        </Button>
                    </form>
                </Box>
            </Box>
        </Flex>
    )
}

export default PostBuilder

const PreviewImage = forwardRef<BoxProps, typeof Box>((props, ref) => {
    return (
        <Box
            bg="white"
            top="0"
            height="100%"
            width="100%"
            position="absolute"
            borderWidth="1px"
            borderStyle="solid"
            rounded="sm"
            borderColor="gray.400"
            backgroundSize="cover"
            backgroundRepeat="no-repeat"
            backgroundPosition="center"
            backgroundImage={`url("https://image.shutterstock.com/image-photo/paella-traditional-classic-spanish-seafood-600w-1662253543.jpg")`}
            {...props}
            ref={ref}
        />
    );
});