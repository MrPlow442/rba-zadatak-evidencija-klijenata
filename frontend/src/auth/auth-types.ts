export interface LoginUser {
    email: string;
    password: string;
}

export interface Login {
    token: string,
    expiresIn: number
}

