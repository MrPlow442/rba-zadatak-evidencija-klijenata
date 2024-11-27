export enum CardRequestStatus {
    PENDING = "PENDING",
    APPROVED = "APPROVED",
    REJECTED = "REJECTED"
}

export interface CardRequest {
    firstName: string;
    lastName: string;
    oib: string;
    status: CardRequestStatus
}