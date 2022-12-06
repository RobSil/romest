import {FC, useState} from "react";
import {
    Button, Checkbox, FormControl, FormErrorMessage, FormLabel, Heading, Input, InputGroup, InputRightElement,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay, Stack, useToast,
} from "@chakra-ui/react";
import BoardService from "../../services/BoardService";
import {useNavigate} from "react-router-dom";

export interface ModalProps {
    isOpen: boolean,
    onClose: () => void,
}

export const validateBoardName = (name: string): boolean => {

    if (name === "") {
        return true
    }

    if (!name || name.length < 3 || name.length > 64) {
        return false
    }

    return true
}

const BoardCreateModal: FC<ModalProps> = ({isOpen, onClose}) => {

    const toast = useToast()
    const navigate = useNavigate()

    const [name, setName] = useState("")
    const [isPrivate, setIsPrivate] = useState(false)

    let isNameValid: boolean = validateBoardName(name)

    const createBoard = () => {

        if (!validateBoardName(name)) {
            toast({
                title: 'Invalid name.',
                description: "Name should longer than 3 chars, and shorter than 64 chars.",
                status: 'error',
                duration: 5000,
                isClosable: true,
            })
            return
        }

        BoardService.create({name, isPrivate})
            .then((req) => {
                if (req.request.status === 201) {
                    toast({
                        title: 'Successfully created a board.',
                        status: 'success',
                        duration: 5000,
                        isClosable: true,
                    })
                    navigate(`/${req.data.user.username}/${req.data.minimizedName}`)
                    return
                }
                if (req.request.status === 400) {
                    if (req.request.response === "Board with similar name is already exists.") {
                        toast({
                            title: 'During creating board occurred error, board with similar name is already exists.',
                            status: 'error',
                            duration: 5000,
                            isClosable: true,
                        })
                        return
                    }
                }
            })
    }

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay/>
            <ModalContent>
                <ModalHeader display="flex" justifyContent={"center"}>
                    <Heading size={"lg"}>Create board</Heading>
                </ModalHeader>
                <ModalCloseButton/>

                <ModalBody>
                    <form>

                        <FormControl isRequired isInvalid={!isNameValid}>
                            <FormLabel>Name</FormLabel>
                            <Input placeholder="Name" onChange={(e) => {
                                setName(e.target.value)
                                isNameValid = validateBoardName(e.target.value)
                            }}/>
                            {!isNameValid && (<FormErrorMessage>Name is invalid.</FormErrorMessage>)}
                        </FormControl>

                        <FormControl mt={4}>
                            <Stack spacing={2} direction='row'>
                                <Checkbox defaultChecked={isPrivate} isChecked={isPrivate} onChange={(e) => {
                                    setIsPrivate(e.target.checked)
                                }}/>
                                <FormLabel>Is private</FormLabel>
                            </Stack>
                        </FormControl>


                    </form>
                </ModalBody>

                <ModalFooter>
                    <Button colorScheme="blue" onClick={() => createBoard()} mr={4}>Create</Button>
                    <Button colorScheme="red" onClick={onClose}>
                        Close
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    )
}

export default BoardCreateModal
