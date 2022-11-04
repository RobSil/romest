import {createBrowserRouter} from "react-router-dom";

const testHome = () => {
    return (
        <>
            <h1>Test Home.</h1>
        </>
    )
}

const router = createBrowserRouter([
    {
        path: "/",
        element: testHome()
    }
])

export default router