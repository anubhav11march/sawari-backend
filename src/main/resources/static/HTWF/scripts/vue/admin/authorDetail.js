Vue.component('authorDetail', {
    data: function (){
        return{
            authorInfo: {},
            authorRights: [],
            files: [],
            roleNames: ['AUTHOR','PRE-EDITOR','EDITOR','ADMIN'],
            typeNames: ['REGISTERED','UNREGISTERED','PAID_SUBSCRIBER','UNPAID_SUBSCRIBER','PRELIMINARY_EDITOR','EDITOR','ADMIN','SUPER_ADMIN','EXPIRED_USER'],
            permissions: ['ReadOnly','WriteOnly','ReadWriteOnly','Blocked'],
            isEnabled: [true, false],
            isDeleted: [true, false],
            perPage: 10,
            currentPage: 1,
        }
    },
    mounted(){
        let urlParams = new URLSearchParams(window.location.search);
        var authorId = urlParams.get('authorId');
        this.getAuthorInfo(authorId)
        this.getAuthorRights(authorId)
        this.getFiles(authorId)
    },
    computed: {
        rows() {
            return this.files.length
        }
    },
    methods: {
        formatDate(inputDate) {
            date = new Date(inputDate);
            return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
        },
        getAuthorInfo(authorId){
            axios.get(`/admin/author?authorId=${authorId}`)
                .then(response => {
                    this.authorInfo = response.data;
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getAuthorRights(authorId){
            axios.get(`/admin/author/rights?authorId=${authorId}`)
                .then(response => {
                    this.authorRights = response.data;
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getFiles(authorId) {
            axios.get(`/admin/author/files?authorId=${authorId}`)
                .then((response) => {
                    var count = 1
                    this.files = response.data.map((file) => ({
                        sNo: count++,
                        topic: file.topic,
                        category: file.category,
                        uploadDate: file.uploadDate,
                        requestServeDate: file.requestServeDate,
                        action: `/admin/editfile?authorId=${file.fileUserId}&fileId=${file.authorFileId}`,
                    }))
                })
                .catch((error) => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        updateRights(){
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            const request = {
                method: 'PUT',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: this.authorRights,
                url: '/admin/author/rights',
            }
            axios(request)
                .then(response =>{
                    swal("Success!", "Author rights has been updated successfully!", "success");
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
    },
    template:
        `<div class="container content">
            <div class="row proporzional-row">
                <div class="col-md-12">
                    <div>
                        <b-tabs active-nav-item-class="font-weight-bold text-uppercase text-danger"  active-tab-class="font-weight-bold text-success"  content-class="mt-3">
                            <b-tab title="Author Info" active>
                                <hr class="space m" />
                                <div class="container">
                                    <div class="row gutters">
                                        <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                                            <div class="card ">
                                                <div class="card-body">
                                                    <form>
                                                        <div class="row gutters">
                                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <h4 class="mb-2 text-primary font-weight-bold">Personal Details</h4>
                                                            </div>
                                                            <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                                                <div class="form-group">
                                                                    <label for="fullName">First (Given) Name</label>
                                                                    <input type="text" class="form-control" id="fullName" v-model="authorInfo.firstName" placeholder="First (Given) Name" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                                                <div class="form-group">
                                                                    <label for="fullName">Middle Name (Optional)</label>
                                                                    <input type="text" class="form-control" id="middleName" v-model="authorInfo.middleName" placeholder="Middle Name (Optional)" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                                                <div class="form-group">
                                                                    <label for="fullName">Last (Family) Name</label>
                                                                    <input type="text" class="form-control" id="lastName" v-model="authorInfo.lastName" placeholder="Last (Family) Name" disabled>
                                                                </div>
                                                            </div>                                
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="phone">Phone</label>
                                                                    <input type="text" class="form-control" id="phone" v-model="authorInfo.phone" placeholder="Phone" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="phone">Mobile</label>
                                                                    <input type="text" class="form-control" id="phone" v-model="authorInfo.mobile" placeholder="Mobile" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="phone">Primary E-Mail Address</label>
                                                                    <input type="email" class="form-control" disabled id="email" v-model="authorInfo.email" placeholder="Primary E-Mail Address" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="phone">Secondary E-Mail</label>
                                                                    <input type="email" class="form-control"  id="email" v-model="authorInfo.alternateEmail" placeholder="Secondary E-Mail" disabled>
                                                                </div>
                                                            </div>                                
                                                        </div>
                                                        <div class="row gutters">
                                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <h4 class="mt-3 mb-2 text-primary font-weight-bold">Address</h4>
                                                            </div>
                                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <div class="form-group">
                                                                    <label for="Street">Address</label>
                                                                    <input type="name" class="form-control" id="address" v-model="authorInfo.street" placeholder="Address" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="ciTy">City</label>
                                                                    <input type="name" class="form-control" id="ciTy" v-model="authorInfo.city" placeholder="Enter City" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="sTate">State</label>
                                                                    <input type="text" class="form-control" id="sTate" v-model="authorInfo.state" placeholder="Enter State" disabled>
                                                                </div>
                                                            </div>
                                                             <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="sTate">Country</label>
                                                                    <input type="text" class="form-control" id="sTate" v-model="authorInfo.country" placeholder="Enter Country" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="zIp">Postal Code</label>
                                                                    <input type="text" class="form-control" id="zIp" v-model="authorInfo.zipCode" placeholder="Postal Code" disabled>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row gutters">
                                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <h4 class="mt-3 mb-2 text-primary font-weight-bold">Education</h4>
                                                            </div>                             
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="ciTy">Institution</label>
                                                                    <input type="name" class="form-control" id="ciTy" v-model="authorInfo.institution" placeholder="Institution" disabled>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label for="sTate">Department</label>
                                                                    <input type="text" class="form-control" id="sTate" v-model="authorInfo.department" placeholder="Department" disabled>
                                                                </div>
                                                            </div>                              
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </b-tab>                            
                            <b-tab title="Author Rights">
                            <hr class="space m" />
                                <div class="card ">
                                    <div class="card-body">
                                        <form>
                                            <div class="row gutters">                                                
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="rolename">Role Name</label>
                                                        <select class="form-control" v-model="authorRights.roleName" name="rolename" id="rolename">                                                            
                                                            <option v-for="roleName in roleNames" :selected="roleName === authorRights.roleName" :value="roleName">{{ roleName }}</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="typename">Type</label>
                                                        <select class="form-control" v-model="authorRights.typeName" name="typename" id="typename">                                                            
                                                            <option v-for="typeName in typeNames" :selected="typeName === authorRights.typeName" :value="typeName">{{ typeName }}</option>                                                            
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="permissionname">Permission</label>
                                                        <select class="form-control" v-model="authorRights.permissionName" name="permissionname" id="permissionname">                                                            
                                                            <option v-for="permission in permissions" :selected="permission === authorRights.permissionName" :value="permission">{{ permission }}</option>
                                                        </select>
                                                    </div>
                                                </div>                                                
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="isdeleted">Deleted</label>
                                                        <select class="form-control" v-model="authorRights.is_deleted" name="isDeleted" id="is_deleted">                                                            
                                                            <option v-for="isDelete in isDeleted" :selected="isDelete === authorRights.is_deleted" :value="isDelete">{{ isDelete }}</option>                                                            
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="isenable">Enabled</label>
                                                        <select class="form-control" v-model="authorRights.is_enable" name="isEnable" id="is_enable">                                                            
                                                            <option v-for="isEnable in isEnabled" :selected="isEnable === authorRights.is_enable" :value="isEnable">{{ isEnable }}</option>                                                            
                                                        </select>
                                                    </div>
                                                </div>                                                                                          
                                            </div>
                                            <div class="row gutters">
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="text-right">
                                                        <button type="submit" id="submit" @click.prevent="updateRights" name="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </b-tab>
                            <b-tab title="Author Files">
                                <div class="container">
                                    <div>            
                                        <hr class="space m" />
                                        <div class="overflow-auto">
                                            <b-table id="my-table" :items="files" :per-page="perPage" :current-page="currentPage" small>
                                                 <template #cell(action)="data">
                                                    <a :href="data.value"> <button type="button" style="background-color: #6A4D8F" class="btn btn-sm">More Info</button></a>
                                                </template>
                                            </b-table>
                                            <p v-show="files.length > perPage" class="mt-3">Current Page: {{ currentPage }}</p>
                                            <b-pagination v-show="files.length > perPage"
                                                v-model="currentPage"
                                                :total-rows="rows"
                                                :per-page="perPage"
                                                aria-controls="my-table"
                                                first-text="First"
                                                prev-text="Prev"
                                                next-text="Next"
                                                last-text="Last"
                                                class="customPagination">
                                            </b-pagination>
                                        </div>
                                    </div>
                                </div>
                            </b-tab>                    
                        </b-tabs>
                    </div>
                </div>
            </div>
        </div>`
})
new Vue({ el: "#author-detail"})