Vue.component('editFile', {
    data: function() {
        return {
            perPage: 10,
            currentPage: 1,
            fileInfo: {},
            reports: [],
            fileStatuses: [],
        }
    },
    mounted() {
        let urlParams = new URLSearchParams(window.location.search);
        var fileId = urlParams.get('fileId')
        var authorId = urlParams.get('authorId')
        this.fetchFileInfo(fileId, authorId)
        this.fetchReports(fileId, authorId)
        this.getFileStatues()
    },
    computed: {
        rows() {
            return this.reports.length
        }
    },
    methods: {
        fetchFileInfo(fileId, authorId) {
            axios.get(`/admin/author/file?authorId=${authorId}&fileId=${fileId}`)
                .then((response) => {
                    this.fileInfo = response.data
                })
                .catch((error) => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        fetchReports(fileId, authorId) {
            axios.get(`/admin/author/file/reports?authorId=${authorId}&fileId=${fileId}`)
                .then((response) => {
                    var count = 1
                    this.reports = response.data.map((report) => ({
                        sNo: count++,
                        type: report.reportType,
                        modifyDate: report.modifyDate,
                        uploadBy: report.userName,
                        action: `/admin/editreport?authorId=${report.authorUserId}&fileId=${report.authorFileId}&reportId=${report.adminFileId}`,
                    }))
                })
                .catch((error) => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        updateFile() {
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            const request = {
                method: 'PUT',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: this.fileInfo,
                url: '/admin/content/status',
            }
            axios(request)
                .then((response) => {
                    swal("Success!", "File status has been updated successfully!", "success");
                })
                .catch((error) => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getFileStatues() {
            axios.get('/init/properties?property=status')
                .then((response) => {
                    this.fileStatuses = response.data
                })
                .catch((error) => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        downloadFile(fileId) {
            window.location.href = `/admin/content/download?fileId=${fileId}`;
        },
        getCreateReportPath(authorFileId,authorUserId){
            return `/admin/content/fileupload?authorUserId=${authorUserId}&authorFileId=${authorFileId}`
        },
    },
    template:
        `<div class="container content">
            <div>
                <b-tabs active-nav-item-class="font-weight-bold text-uppercase text-danger"
                        active-tab-class="font-weight-bold text-success"
                        content-class="mt-3">
                    <b-tab title="Edit File" active>
                        <br>
                        <button @click="downloadFile(fileInfo.authorFileId)" class="btn btn-sm" type="button" style="color: white">
                                <i class="fa fa-file-image-o"></i>Download File
                        </button>
                        <hr class="space">
                        <div class="card">
                            <div class="card-body">
                                <form>
                                    <div class="row gutters">
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="topic">Topic</label>
                                                <input type="text" class="form-control" id="topic" v-model="fileInfo.topic" name="topic" disabled>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="category">Category</label>
                                                <input type="text" class="form-control" id="category" v-model="fileInfo.category" name="category" disabled>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="language">Language</label>
                                                <input type="text" class="form-control" id="language" v-model="fileInfo.language" name="language" disabled>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="wordscount">Words Count</label>
                                                <input type="text" class="form-control" id="wordscount" v-model="fileInfo.wordsCount" name="wordcount" disabled>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="uploadDate">Upload Date</label>
                                                <input type="text" class="form-control" id="uploadDate" v-model="fileInfo.uploadDate" name="uploadDate" disabled>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="requestServeDate">Request Serve Date</label>
                                                <input type="text" class="form-control" id="requestServeDate" v-model="fileInfo.requestServeDate" name="requestServeDate" disabled>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="services">Services I want</label>
                                                <input type="text" class="form-control" id="requestedServices" v-model="fileInfo.requestedServices" name="requestedServices" disabled>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="status">Status</label>
                                                <select class="form-control" v-model="fileInfo.fileStatus" aria-label="Default select example">
                                                    <option v-for="status in fileStatuses" :selected="fileInfo.fileStatus === status" :value="status">{{ status }}</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label for="description">Description</label>
                                                <textarea class="form-control" id="description" v-model="fileInfo.description" disabled></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row gutters">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="text-right">
                                                <button type="submit" id="submit" @click.prevent="updateFile" name="submit" class="btn btn-primary">Update</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </b-tab>
                    <b-tab title="Reports">
                        <br>
                        <button class="btn btn-sm" type="button">
                            <a :href="getCreateReportPath(fileInfo.authorFileId,fileInfo.fileUserId)" style="color: white">
                                <i class="fa fa-file-image-o"></i>New Report
                            </a>
                        </button>
                        <br>
                        <hr class="space m" />
                        <b-table id="my-table" :items="reports" :per-page="perPage" :current-page="currentPage" small>
                            <template #cell(action)="data">
                                <a :href="data.value"> <button type="button" style="background-color: #6A4D8F" class="btn btn-sm">More Info</button></a>
                            </template>
                        </b-table>
                    </b-tab>
                </b-tabs>
            </div>
        </div>`
})

new Vue({ el: "#edit-file"})