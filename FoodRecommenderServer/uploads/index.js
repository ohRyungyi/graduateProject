const express = require('express');
const fs = require('fs');
const request = require('request');

const router = express.Router();

router.get('/' , function(req, res, next) {
    console.log('/images start ------------------------------------')
    console.log('url : '+req.query.name)

    var filename = "./uploads/"+req.query.name

    fs.readFile(filename , function(err, data) {
        if (err) {
            res.writeHead(400, { "Context-Type": "image/jpg" });//보낼 헤더를 만듬
            // res.write(data);   //본문을 만들고
            res.end();
        }
        else{
            res.writeHead(200, { "Context-Type": "image/jpg" });//보낼 헤더를 만듬
            res.write(data);   //본문을 만들고
            res.end();
        }
    })
    

    // var status = 200;
    // var result = new Object()

    // const options = {
    //     url : 'http://6158-58-238-222-71.ngrok.io/uploads/20211220326162.jpeg',
    //     method : 'GET',
    //     encoding: null
    // };
    // request( options , (err, response , body) => {
    //     res.set('Content-Type' , response.headers['content-type']);
    //     res.send(body);
    // })

    // return res.send('http://6158-58-238-222-71.ngrok.io/uploads/20211220326162.jpeg')

    // return res.status(200).json(result)
});

module.exports = router;