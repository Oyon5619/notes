import request from "@/utils/request"

// export const uploadFileApi = (file) => {
//   const formData = new FormData()
//   formData.append('file', file)

//   return request({
//     method: 'POST',
//     url: '/file/upload',
//     data: formData,
//   })
// }

// export const getFileApi = (name) => {
//   return request({
//     method: 'GET',
//     url: `/file/download?name=${name}`,
//   })
// }

export const testApi = () => {
  return request.get('/photo/test')
}