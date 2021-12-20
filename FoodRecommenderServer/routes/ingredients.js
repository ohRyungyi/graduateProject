const express = require('express');
const multer = require('multer');
const { execSync, exec } = require("child_process");
const { JSONParser } = require('formidable');

const router = express.Router();

// var storage = multer.diskStorage({
//     destination : function( req, file , callback ) {
//         callback( null , 'images/' );
//     },
//     filename: function( req , file , callback ) {
//         callback( null , file.originalname );
//     }
// });
// const upload = multer({
//     storage: multer.diskStorage({
//         dest : function( req, file , callback ) {
//             callback( null , 'images/' );
//         },
//         filename: function( req , file , callback ) {
//             callback( null , file.originalname );
//         }
//     }),
//     limits: {
//         files: 10, // 파일 수 제한
//         fileSize: 1024*1024*1024 // 이미지 파일 크기 제한
//     }
// });

const upload = multer({ 
    storage: multer.diskStorage({
        destination: function(req, file , callback ) {
            callback(null , "uploads/");
        },
        filename: function( req, file, callback ) {
            callback(null , file.originalname );
        }
    }),
    limits: { 
        fileSize: 1024 * 1024 * 1024 
    } 
});

router.post( '/runYoloModel' , upload.array('img' , 10) , function( req , res , next) {
    console.log('/ingredients/runYoloModel start --------------------------------------');

    console.log("body : "+JSON.stringify( req.body));
    console.log("file : "+ JSON.stringify(req.file));
    console.log("files : "+JSON.stringify(req.files));

    var result = new Object();

    let images = req.files;

    if ( req.files == null && req.file == null ) {
        result.success = false;
        result.msg = "cannot find images"
        result.data = null

        console.log("response : "+JSON.stringify(result))
        return res.status(401).json(result)
    }
    else if( Array.isArray(images) ) {
        // 여러가지 이미지가 업로드
        let results = new Array();
        let isSuccess  = false;

        for ( var index = 0 ; index < images.length ; index++ ) {
            let path = images[index].path;
            let originalname = images[index].originalname;
            
            let yolo = exec( 'python3 yolo.py '+path , (err , stdout , stderr) => {
                if (err) {
                    // 에러 발생
                    isSuccess = false;
                    
                    var data = new Object();
                    data.order = index
                    data.ingredients = [];

                    results.push( data)
                    // var result = new Object();
                    // result.success = false;
                    // result.msg = "fail to run yolo model";
                    // result.data = null;

                    // console.log("response : "+JSON.stringify(result))
                    // return res.status(400).json(result);
                }

                // stdout 형태 : ['식재료1' , '식재료2']\n이미지 경로
                ingredient_list = stdout.split('\n')[0]
                
                let result_ingredients = []

                for ( var i=0; i< ingredient_list.length ; i++  ) {
                    result_ingredients.push( ingredient_list[i].replace('\'' , '\"') );
                }

                var data = new Object();

                data.img = originalname;
                data.ingredients = result_ingredients;

                results.push( data )
            })

        }

        if ( isSuccess == false ) {
            result.success = false;
            result.msg = "fail to run Yolo Model"
            // result.data = null

            // var dataList = new Array();
            var data = new Object();
            // data.order = 0;
            // data.ingredients = "감자,고구마,양파"
            // dataList.push(data)

            // var data = new Object();
            // data.order = 1;
            // data.ingredients = "호박,당근"
            // dataList.push(data)

            result.data = "감자,고구마,양파/호박,당근";
            // result.data = dataList

            console.log("response : "+JSON.stringify(result))
            return res.status(200).json(result)
        }
        else {
            result.success = true;
            result.msg = "success to get ingredients";
            result.data = results
            console.log("response : "+JSON.stringify(result))
            return res.status(200).json(result)
        }
    }

    // console.log('/ingredients/runYoloModel end -------------------------------------------------');
});

router.route('/doubleCheck' , function(req ,res , next) {
    console.log('/ingredients/runYoloModel --------------------------------------');
});

module.exports = router;