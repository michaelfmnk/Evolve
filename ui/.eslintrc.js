module.exports = {
    "extends": ["standard", "standard-react"],
    "parser": "babel-eslint",
    "rules": {
        "indent": [
            "error",
            2
        ],
        "no-console": "warn",
        "no-extra-semi": "error",
        "jsx-quotes": "off",
        "max-len": [
            "error", 
            { 
             "code": 120 ,
             "ignoreStrings": true,
             "ignoreUrls": true,
             "ignoreTemplateLiterals": true,
             "ignoreRegExpLiterals": true 
            },
         ],
         "arrow-spacing": "error",
         "generator-star-spacing": "error",
    },
    "globals": { "fetch": false , "localStorage": true, "process": true, "it": true },
};