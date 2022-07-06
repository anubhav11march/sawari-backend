Vue.component('editReport',{
    data: function(){
      return {
          reportInfo: {},
          categories: [],
          languages: [],
          services: [],
          reportTypes: ['finalReport','preliminaryReport'],
      }
    },
    mounted(){
        let urlParams = new URLSearchParams(window.location.search);
        this.getReportInfo(urlParams.get('reportId'),urlParams.get('fileId'),urlParams.get('authorId'))
        this.getCategories()
        this.getLanguages()
        this.getServices()
        $('#services').select2();
    },
    methods:{
      getReportInfo(reportId,fileId,authorId){
          axios.get(`/admin/author/file/report?fileId=${fileId}&authorId=${authorId}&reportId=${reportId}`)
              .then(response =>{
                  this.reportInfo = response.data
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
            axios.get("/admin/plans")
                .then(response => {
                    this.services = response.data.map(service => ({name: service.name, price: service.price}))
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        updateReport() {
            var selectedServices = $($("#services")[0]).val();
            this.reportInfo.proposedServices = selectedServices.join(',')
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            const request = {
                method: 'PUT',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: this.reportInfo,
                url: '/admin/content/update',
            }
            axios(request)
                .then(response =>{
                    swal("Success!", "Report has been updated successfully!", "success");
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })

        },
        downloadReport(reportId,fileId){
            window.location.href = `/admin/content/download?isReport=Y&reportId=${reportId}&fileId=${fileId}`;
        },
        compareValues(initialValue, commaSeparatedValues){
            if(commaSeparatedValues)
                return commaSeparatedValues.split(',').filter(value => value === initialValue).length > 0
            else
                return false;
        },
    },
    template:
        `<div>
            <div class="section-empty">
                <div class="container content">
                    <button @click="downloadReport(reportInfo.adminFileId,reportInfo.authorFileId)" class="btn btn-sm" type="button" style="color: white">
                        <i class="fa fa-file-image-o"></i>Download Report
                    </button>
                    <hr class="space">
                    <div class="container">
                        <div class="row gutters">
                            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                                <div class="card ">
                                    <div class="card-body">
                                        <form action="" method="post">
                                            <div class="row gutters">
                                                <input type="hidden" id="authorFileId" name="authorFileId" value="">
                                                <input type="hidden" id="authorUserId" name="authorUserId" value="">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="topic">Topic</label>
                                                        <input type="text" class="form-control" id="topic" v-model="reportInfo.topic" name="topic"
                                                               placeholder="Manuscript, Figure only, Poster, Thesis, Grant">
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="category">Category</label>
                                                        <select class="form-control" v-model="reportInfo.category" name="category" id="category">
                                                            <option v-for="category in categories" :selected="category === reportInfo.category" :value="category" >{{ category }}</option> 
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="language">Language</label>
                                                        <select class="form-control" v-model="reportInfo.language" name="languages" id="languages">
                                                            <option v-for="language in languages" :selected="language === reportInfo.language" :value="language" >{{ language }}</option> 
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="reportType">Report Type</label>
                                                        <select class="form-control" v-model="reportInfo.reportType" name="report-type" id="report-type">
                                                            <option v-for="reportType in reportTypes" :selected="reportType === reportInfo.reportType" :value="reportType" >{{ reportType }}</option> 
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="description">Description</label>
                                                        <textarea name="description" id="description" v-model="reportInfo.description"
                                                                  class="form-control"></textarea>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="services">Proposed Services</label>
                                                        <select class="form-control" v-model="reportInfo.proposedServices" name="services[]" id="services" multiple="multiple">
                                                            <option v-for="service in services" :selected="compareValues(service.name, reportInfo.proposedServices)"   :value="service.name" >{{ service.name }} ($\{{ service.price }})</option> 
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gutters">
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="text-right">
                                                        <button type="submit" id="submit" @click.prevent="updateReport" name="submit" class="btn btn-primary">Update</button>
                                                  
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`
})

new Vue({ el:'#edit-report' })