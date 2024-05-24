function expiredJwt(token) {
    try {
        const base64Url = token.split('.')[1]; // get the payload part
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        const decodedToken = JSON.parse(jsonPayload);
        const expired = isTokenExpired(decodedToken);
        const role = decodedToken.role || null;

        return [role, expired];
    } catch (error) {
        return [null, true]; // If there's an error, return null for role and true for expired
    }
}

function isTokenExpired(decodedToken) {
    if (!decodedToken || !decodedToken.exp) {
        console.log("Failed to decode token or token is empty.");
        return true; // Consider the token expired if it cannot be decoded
    }

    const currentTime = Date.now() / 1000; // convert milliseconds to seconds
    return decodedToken.exp < currentTime;
}

export default expiredJwt;
