前端代码：https://github.com/xingyuv/captcha-plus

为了方便修改测试，复制过来。

+ vue3

  Node17+ 需要修改`package.json`以及`axios.js`的`baseURL`。

  ```json
  {
    "scripts": {
      "serve": "export NODE_OPTIONS=--openssl-legacy-provider && vue-cli-service serve",
    }
  }
  ```