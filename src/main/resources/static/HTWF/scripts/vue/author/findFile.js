Vue.component('findFile', {
    data: function() {
        return {
            perPage: 10,
            currentPage: 1,
            files: [],
            fields: [],
              
        }
    },
    mounted(){
        this.fields = [
            { key: 'sNo', label: `${i18next.t('sNo')}` },
            { key: 'topic', label: `${i18next.t('topic')}` },
            { key: 'category', label: `${i18next.t('category')}`},
            { key: 'uploadDate', label: `${i18next.t('uploadDate')}` },
            { key: 'requestServeDate', label: `${i18next.t('requestServeDate')}` },
            { key: 'status', label: `${i18next.t('status')}` },
            { key: 'action', label: `${i18next.t('action')}` },
        ]
        this.getFiles()
    },
    computed: {
        rows() {
            return this.files.length
        }
    },
    methods: {
        getFiles() {
            axios.get('/author/content/files')
                .then(response => {
                    var count = 1
                    this.files = response.data.map((file) => ({
                        sNo: count++,
                        topic: file.topic,
                        category: file.category,
                        uploadDate: file.uploadDate,
                        requestServeDate: file.requestServeDate,
                        status: file.fileStatus,
                        action: `/author/content/editfile?fileId=${file.authorFileId}`,
                    }))
                })
                .catch(error => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        formatDate(inputDate) {
            date = new Date(inputDate);
            return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
        },
    },
    template:
        `<div>
            <button class="btn btn-sm" type="button">
                <a href="/author/content/fileupload" style="color: white">
                    <i class="fa fa-file-image-o"></i>${i18next.t('newFile')}
                </a>
            </button>
            <hr class="space m" />
            <div class="overflow-auto">
                <b-table id="my-table" :fields="fields" :items="files" :per-page="perPage" :current-page="currentPage" small>
                    <template #cell(action)="data">
                        <a :href="data.value"> <button type="button" style="background-color: #6A4D8F" class="btn btn-sm">${i18next.t('moreInfo')}</button></a>
                    </template>
                </b-table>
                <p v-show="files.length > perPage" class="mt-3">${i18next.t('currentPage')}: {{ currentPage }}</p>

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
        </div>`
})

new Vue({ el: "#findFile" })