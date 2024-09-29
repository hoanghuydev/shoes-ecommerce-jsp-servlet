const conditionTables = (value) => `
<div class="input-group input-group-static mb-4">
                  <label for="applyFor" class="ms-0">Áp dụng với</label>
                  <select class="form-control" id="applyFor" name="tableName" required>
                    <option value="user" ${value==="user" ? "selected" : ""}>Người dùng</option>
                    <option value="product" ${value==="product" ? "selected" : ""}>Sản phẩm</option>
                    <option value="category" ${value==="category" ? "selected" : ""}>Thể loại sản phẩm</option>
                  </select>
                </div>
`
const conditionColumns = {
    user : (value) =>  `<div class="input-group input-group-static mb-4 ">
                          <label for="columnName" class="ms-0">Thông tin áp dụng điều kiện</label>
                          <select class="form-control" id="columnName" name="columnName" required>
                            <option value="id" ${value==="id" ? "selected" : ""}>ID người dùng</option>
                            <option value="fullName" ${value==="fullName" ? "selected" : ""}>Tên người dùng</option>
                            <option value="association" ${value==="association" ? "selected" : ""}>Liên kết</option>
                            <option value="createAt" ${value==="createAt" ? "selected" : ""}>Thời gian tài khoản đã được tạo</option>
                          </select>
                        </div>`,
    product : (value)=> `
    <div class="input-group input-group-static mb-4 ">
                          <label for="columnName" class="ms-0">Thông tin áp dụng điều kiện</label>
                          <select class="form-control" id="columnName" name="columnName" required>
                            <option value="id" ${value==="id" ? "selected" : ""}>ID sản phẩm</option>
                            <option value="name" ${value==="name" ? "selected" : ""}>Tên sản phẩm</option>
                            <option value="price" ${value==="price" ? "selected" : ""}>Giá tiền</option>
                          </select>
                        </div>
    `,
    category : (value)=>`
    <div class="input-group input-group-static mb-4 ">
                          <label for="columnName" class="ms-0">Thông tin áp dụng điều kiện</label>
                          <select class="form-control" id="columnName" name="columnName" required>
                            <option value="id" ${value==="id" ? "selected" : ""}>ID thể loại</option>
                            <option value="name" selected>Tên thể loại</option>
                          </select>
                        </div>
    `
}
const defaultColumnCondition = "id";
const conditionValue = {
    user : {
        id : (value) => `
            <div class="input-group input-group-outline my-3">
                  <label class="form-label">Điều kiện cụ thể</label>
                  <input type="text" class="form-control" name="id" value="${value}" required/>
                </div>
        `,
        fullName : (value)=> `
        <div class="input-group input-group-outline my-3">
                  <label class="form-label">Điều kiện cụ thể (Có tên giống)</label>
                  <input type="text" class="form-control" name="fullName" value="${value}" required/>
                </div>
        `,
        association : (value) => `
        <div class="input-group input-group-static mb-4">
                          <label for="association" class="ms-0">Điều kiện cụ thể</label>
                          <select class="form-control" id="association" name="association" required>
                            <option value="" ${value==="" ? "selected" : ""}>--Chọn tài khoản liên kết--</option>
                            <option value="google" ${value==="google" ? "selected" : ""}>Google</option>
                            <option value="facebook" ${value==="facebook" ? "selected" : ""}>Facebook</option>
                          </select>
                        </div>
        `,
        createAt : (value) => `
        <div class="input-group input-group-static mb-4">
                          <label for="size" class="ms-0">Điều kiện cụ thể</label>
                           <input type="date" class="form-control" name="createAt" value="${value}" required/>
                      </div>
        `

    },
    product: {
        id : (value) => `
            <div class="input-group input-group-outline my-3">
                  <label class="form-label">Điều kiện cụ thể</label>
                  <input type="text" class="form-control" name="id" value="${value}" required/>
            </div>
        `,
        name : (value) =>`
        <div class="input-group input-group-outline my-3">
                  <label class="form-label">Điều kiện cụ thể (Có tên giống)</label>
                  <input type="text" class="form-control" name="name" value="${value}" required/>
                </div>
        `,
        price : (value) => `
        <div class="input-group input-group-outline my-3">
                  <label class="form-label">Điều kiện cụ thể (Lớn hơn giá tiền)</label>
                  <input type="text" class="form-control" value="${value}" name="price" required/>
                </div>
        `
    },
    category : {
        id : (value) => `
            <div class="input-group input-group-outline my-3">
                  <label class="form-label">Điều kiện cụ thể</label>
                  <input type="text" class="form-control" name="id" value="${value}" required/>
                </div>
        `,
        name : (value) => `
            <div class="input-group input-group-static mb-4">
                          <label for="category" class="ms-0">Điều kiện cụ thể</label>
                          <select class="form-control" id="category" name="category" required>
                            <option value="1" ${value==="1" ? "selected" : ""}>Giày thường ngày</option>
                            <option value="2" ${value==="2" ? "selected" : ""}>Giày bóng rổ</option>
                            <option value="3" ${value==="3" ? "selected" : ""}>Giày bóng đá</option>
                             <option value="4" ${value==="4" ? "selected" : ""}>Giày bóng chày</option>
                            <option value="5" ${value==="5" ? "selected" : ""}>Giày bóng chuyền</option>
                            <option value="6" ${value==="6" ? "selected" : ""}>Giày golf</option>
                            <option value="7" ${value==="7" ? "selected" : ""}>Giày chạy bộ</option>
                          </select>
                        </div>
            `
    }

}