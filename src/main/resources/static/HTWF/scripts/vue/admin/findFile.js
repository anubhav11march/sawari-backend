Vue.component('findFile', {
    data: function() {
        return {
            perPage: 10,
            currentPage: 1,
            files: [],
        }
    },
    mounted() {
        this.getFiles()
    },
    computed: {
        rows() {
            return this.files.length
        }
    },
    methods: {
        getFiles() {
            axios.get('/admin/authors/files')
            .then((response) => {
                var count = 1
                this.files = response.data.map((file) => ({
                    sNo: count++,
                    topic: file.topic,
                    category: file.category,
                    uploadDate: file.uploadDate,
                    requestServeDate: file.requestServeDate,
                    author: file.fileUserId,
                    action: `/admin/editfile?authorId=${file.fileUserId}&fileId=${file.authorFileId}`,
                }))
            })
            .catch((error) => {
                swal("Error!", error.response.data.message, "error");
            })
        },
        getAuthorUrl(authorId){
            return `/admin/authorinfo?authorId=${authorId}`
        },
        formatDate(inputDate) {
            date = new Date(inputDate);
            return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
        }
    },
    template:
        `<div class="container content">
            <div class="overflow-auto">
                <b-table id="my-table" :items="files" :per-page="perPage" :current-page="currentPage" small>
                     <template #cell(author)="data">
                        <a :href="getAuthorUrl(data.value)" style="color: blue; text-decoration: underline;">{{ data.value }}</a>
                    </template>
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
        </div>`
})

new Vue({ el: "#find-file"})