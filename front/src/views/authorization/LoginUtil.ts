

export const EMAIL_REGEX: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/


export const validateEmail = (email: string): boolean => {

    if (email === "") {
        return true
    }

    if (!email) {
        return false
    }

    return email.match(EMAIL_REGEX) != null
}

export const validatePassword = (password: string, min: number): boolean => {

    if (password === "") {
        return true
    }

    if (!password) {
        return false
    }

    if (password.length < min || password.length > 32) {
        return false
    }

    return true
}