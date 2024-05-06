function expiredJwt(token) {
    try {
        const base64Url = token.split('.')[1]; // get the payload part
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        const expired = isTokenExpired(jsonPayload);
        console.log(expired);
        return expired;

    } catch (error) {
        return true;
    }
}
export default expiredJwt;

function isTokenExpired(decodedToken) {
    if (!decodedToken) {
        console.log("Failed to decode token or token is empty.");
        return true; // Consider the token expired if it cannot be decoded
    }

    const currentTime = Date.now() / 1000; // convert milliseconds to seconds
    return decodedToken.exp < currentTime;
}