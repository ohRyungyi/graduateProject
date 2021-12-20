// import createError from 'http-errors';
// import express from 'express';
// import response from './utils/response.js';
// import logger from 'morgan';
// import router from './routes/api/index.js';

// import multer from 'multer';
// import exec from 'child-process';
// // const exec = require('child-process')
// import { stderr } from 'process';

// var storage = multer.diskStorage({
// 	destination: function (req, file, cb) {
// 		cb(null, 'img/')
// 		//using multer lib, upload image for a badge
// 	},
// 	filename: function (req, file, cb) {
// 		cb(null, file.originalname)
// 		//현재날짜_파일이름
// 	}
// })

// var upload = multer({ 
//     storage: storage,
//     limits: {
//         files: 10,
//         fileSize: 1024*1024*1024
//     }
//  });

// const app = express();

// app.use( logger(":method :url :date :status" , function( req , res , next) { next(); }));

// app.use( express.json({ limit : 7000000 }));
// app.use( express.json({ 
//     limit : 7000000 ,
//     extended : true ,
//     parameterLimit : 7000000
// }));


// // Catch 404 and forward to error handler
// // app.use((req, res, next) => {
// //     next(createError(404));
// //   });
  
// //   // Error handler
// //   app.use((err, req, res, next) => {
// //     let apiError = err;
  
// //     if (!err.status) {
// //       apiError = createError(err)
// //     }
  
// //     // Set locals, only providing error in development
// //     res.locals.message = apiError.message;
// //     res.locals.error = process.env.NODE_ENV === 'development' ? apiError : {};
  
// //     // Render the error page
// //     return response(res, {
// //       message: apiError.message
// //     }, apiError.status)
// //   });


// app.use('/' , router);

// app.post('/runYolo' , upload.array('img') , (req ,res) => {
//     console.log("파일 업로드했습니다.")

//     let files = req.files;
//     let results = new Array();
    
//     for ( var index = 0; index<files.length ; index++ ) {
//         let result = new Object(); // { "img" : "이미지 경로" , "ingredients" : [ 인식한 식재료 목록 ]} 형태

//         let path = file[index].path;

//         let yolo = exec( 'python3 yolo.py '+path , (err , stdout , stderr) => {
//             if ( err ) {
//                 // 에러 존재
//                 return res.status(400).json({
//                     'success' : false,
//                     'msg' : 'fail to run yolo model',
//                     'data' : null
//                 });
//             }

//             ingredients = stdout.split('\n')[1]
//             ingredient_list = ingredients.split(',')
            
//             let result_ingredients = []

//             for ( var i=0; i< ingredient_list.length ; i++  ) {
//                 result_ingredients.push( ingredient_list[i].replace('\'' , '\"') );
//             }

//             result.img = path;
//             result.ingredients = result_ingredients;

//             results.push( result )

//         })
//     }

//     console.log('[runYolo] response data : '+results)

//     return res.status(200).json({
//         'success' : true ,
//         'msg' : 'success to run yolo model',
//         'data' : results
//     });
// })


// //   app.get('/checkIngredients' , (req, res) => {
// //       console.log("[checkIngredients] request body : ");

// //       return res.status(200).json({'success' : true});
// //   })

// //   app.get('/searchRecipeByName' , (req , res) => {
// //       console.log("[searchRecipeByName] request body : " )

// //       return res.status(200).json({'success' : true})
// //   });

// //   app.get('/searchRecipeByIngredients' , (req , res) => {
// //       console.log("[searchRecipeByIngredients] request body : ");

// //       return res.status(200).json({'success' : true})
// //   });

// //   app.get('/getRecipe' , (req, res) => {
// //       console.log("[getRecipe] request body : (name) "+req.body.name);

// //       return res.status(200).json({'success' : true})
// //   });

//   app.listen( 3000, function() {
      
//   })

//   export default app;

const express = require('express');
const logger = require('morgan');
const fs = require('fs');

const ingredientRouter = require('./routes/ingredients.js') // 식재료 관련 router
const recipeRouter = require('./routes/recipes.js') // 레시피 관련 router
const imageRouter  = require('./uploads/index.js')

const app = express();
const port = 3000;

app.use( logger(":method :url :date :status" , function( req , res , next) { next(); }));

app.use( express.json({ limit : 100000000 }));
app.use( express.json({ 
    limit : 100000000 ,
    extended : true ,
    parameterLimit : 100000000
}));

app.use('/ingredients' , ingredientRouter);
app.use('/recipes' , recipeRouter);
app.use('/images' , imageRouter)
app.use(express.static('public'));

app.listen( port , function() {

});