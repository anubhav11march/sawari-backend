Vue.component('editFile', {
    data: function() {
        return {
            perPage: 10,
            currentPage: 1,
            fileInfo: {},
            categories: [],
            languages: [],
            services: [],
            reports: [],
            reportFields: [],
        }
    },
    mounted(){
        let urlParams = new URLSearchParams(window.location.search);
        var fileId = urlParams.get('fileId')
        this.getFileInfo(fileId)
        this.getCategories()
        this.getLanguages()
        this.getServices()
        this.getReports(fileId)
        $('#services').select2();
        this.reportFields = [
            { key: 'sNo', label: `${i18next.t('sNo')}` },
            { key: 'type', label: `${i18next.t('type')}` },
            { key: 'modifyDate', label: `${i18next.t('modifyDate')}`},
            { key: 'uploadBy', label: `${i18next.t('uploadBy')}` },
            { key: 'action', label: `${i18next.t('action')}` },
        ]
    },
    computed: {
        rows() {
            return this.reports.length
        }
    },
    methods: {
        getFileInfo(fileId) {
            axios.get(`/author/content/file?fileId=${fileId}`)
                .then(response => {
                    this.fileInfo = response.data;
                })
                .catch(error => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getReports(fileId) {
            axios.get(`/author/content/reports?fileId=${fileId}`)
                .then((response) => {
                    var count = 1
                    this.reports = response.data.map((report) => ({
                        sNo: count++,
                        type: report.reportType,
                        modifyDate: report.modifyDate,
                        uploadBy: report.userName,
                        action: `/author/reportinfo?fileId=${fileId}&reportId=${report.adminFileId}`,
                    }))
                })
                .catch((error) => {
                    // swal("Error!", error.response.data.message, "error");
                })
        },
        updateFile() {
            var selectedServices = $($("#services")[0]).val();
            this.fileInfo.requestedServices = selectedServices.join(',')
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            const request = {
                method: 'PUT',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: this.fileInfo,
                url: '/author/content/update',
            }
            axios(request)
                .then(response =>{
                    swal("Success!", "File has been updated successfully!", "success");
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })

        },
        getCategories() {
            axios.get("/init/properties?property=category")
                .then(response => {
                    this.categories = response.data
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getLanguages() {
            axios.get("/init/properties?property=language")
                .then(response => {
                    this.languages = response.data
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getServices() {
            axios.get("/author/plans")
                .then(response => {
                    this.services = response.data.map(service => ({name: service.name, price: service.price}))
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        compareValues(initialValue, commaSeparatedValues){
            if(commaSeparatedValues)
                return commaSeparatedValues.split(',').filter(value => value === initialValue).length > 0
            else
                return false;
        },
        downloadFile(fileId) {
            window.location.href = `/author/content/download?fileId=${fileId}`;
        },
    },
    template:
        `<div>
            <b-tabs active-nav-item-class="font-weight-bold text-uppercase text-danger"
                    active-tab-class="font-weight-bold text-success"
                    content-class="mt-3">
                <b-tab title="Edit File" active>
                    <br>
                    <button @click="downloadFile(fileInfo.authorFileId)" class="btn btn-sm" type="button" style="color: white">
                            <i class="fa fa-file-image-o"></i>${i18next.t('downloadFile')}
                    </button>
                    <hr class="space">
                    <div class="card ">
                        <div class="card-body">
                            <form>
                                <div class="row gutters">
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="topic">${i18next.t('topic')}</label>
                                            <input type="text" class="form-control" id="topic" v-model="fileInfo.topic" name="topic">
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="category">${i18next.t('category')}</label>
                                            <select class="form-control" v-model="fileInfo.category" name="category" id="category">
                                                <option v-for="category in categories" :selected="category === fileInfo.category" :value="category" >{{ category }}</option> 
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="language">${i18next.t('language')}</label>
                                            <select class="form-control" v-model="fileInfo.language" name="languages" id="languages">
                                                <option v-for="language in languages" :selected="language === fileInfo.language" :value="language" >{{ language }}</option> 
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="wordscount">${i18next.t('wordsCount')}</label>
                                            <input type="text" class="form-control" id="wordscount" v-model="fileInfo.wordsCount" name="wordcount">
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="uploadDate">${i18next.t('uploadDate')}</label>
                                            <input type="text" class="form-control" id="uploadDate" v-model="fileInfo.uploadDate" name="uploadDate" disabled>
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="requestServeDate">${i18next.t('requestServeDate')}</label>
                                            <input type="date" class="form-control" id="requestServeDate" v-model="fileInfo.requestServeDate" name="requestServeDate">
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="description">${i18next.t('description')}</label>
                                            <textarea class="form-control" id="description" v-model="fileInfo.description"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label for="services">${i18next.t('servicesIWant')}</label>                          
                                            <select class="form-control" v-model="fileInfo.requestedServices" name="services[]" id="services" multiple="multiple">
                                                <option v-for="service in services" :selected="compareValues(service.name, fileInfo.requestedServices)"  :value="service.name" >{{ service.name }} ($\{{ service.price }})</option> 
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row gutters">
                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                        <div class="text-right">
                                            <button type="submit" id="submit" @click.prevent="updateFile" name="submit" class="btn btn-primary">${i18next.t('update')}</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </b-tab>
                <b-tab title="Reports">
                    <br>
                    <b-table id="my-table" :fields="reportFields" :items="reports" :per-page="perPage" :current-page="currentPage" small>
                        <template #cell(action)="data">
                            <a :href="data.value"> <button type="button" style="background-color: #6A4D8F" class="btn btn-sm">${i18next.t('moreInfo')}</button></a>
                        </template>
                    </b-table>
                </b-tab>
            </b-tabs>
        </div>`
})

new Vue({ el: "#editFile" })