
const path = require('path')

module.exports = {  
	outputDir: path.resolve(__dirname, "../"+"/WebContent/resources"),
	indexPath: path.resolve(__dirname, "../"+"/WebContent/WEB-INF/views/index.html"),
  devServer: {
	  proxy: {
		  '/api': {
			  target: 'http://localhost:8081',
			  ws: true,
			  changeOrigin: true
		  },
	  }
  }
}