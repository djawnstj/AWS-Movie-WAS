export default class Common {

    static protocol = "http";
    static host = "localhost";
    static port = "8080";
    static baseUri = "/aws-movie-api/v1";
    static baseUrl = `${this.protocol}://${this.host}:${this.port}${this.baseUri}`;

}