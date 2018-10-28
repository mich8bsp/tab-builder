import axios from 'axios';

export default class HTTPClientService{

    sendToServer(data) {
        console.log('sending data to server', data);

         axios.post(`http://localhost:8888/data-submit/`, data)
              .then(res => {
               console.log("data sent")
              });
    }

}