export enum CardRequestStatus {
    PENDING = "PENDING",
    APPROVED = "APPROVED",
    REJECTED = "REJECTED"
}

export interface Client {
    firstName: string;
    lastName: string;
    oib: string;
    status: CardRequestStatus
}