Vue.component('forgotPassword', {
    data: function (){
        return{

            email: '',
            validationMessage: [],
            buttonText: i18next.t('resetPassword'),
        }
    },
    watch: {
        email(value) {
            this.email = value
            this.validateEmail(value)
        },
    },
    methods: {
        validateEmail(value) {
            if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
                this.validationMessage.email = ''
            } else {
                this.validationMessage.email = `${i18next.t('invalidEmail')}`
            }
        },
        sendEmail(){
            var _csrf = $("meta[name='_csrf']").attr("content");
            this.buttonText = 'Sending Mail';
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            var self = this;
            var data = {email: this.email,}
            const request = {
                method: 'POST',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: data,
                url: '/init/forgot-password',
            }
            axios(request)
                .then(function (response) {
                    this.buttonText = `${i18next.t('resetPassword')}`
                    swal("Success!", `${i18next.t('passwordResetMessage')}`, "success");
                    setTimeout(function(){
                        window.location.href = "/login"

                    }, 3000);


                })
                .catch(function (error) {
                    console.log(this.buttonText)
                    this.buttonText = `${i18next.t('resetPassword')}`
                    swal("Error!", error.response.data.message, "error");
                    setTimeout(function(){
                        window.location.href = "/login"

                    }, 3000);
                });


        },
    },
    template:
        `
<div>
<div class="container" style="margin-top: -65px">
        <div class="row gutters">
            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                <div class="card ">
                    <div class="card-body">
                        <form @submit.prevent="sendEmail">
                            <div class="row gutters">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="phone">${i18next.t('primaryEmail')}</label>
                                        <input type="email" class="form-control" v-model="email"  placeholder="${i18next.t('enterEmail')}" required>  
                                    </div>
                                    <span class="text text-danger text-sm" v-if="validationMessage.email">{{ validationMessage.email }}</span>
                                </div>
                            </div> 
                            <div class="row gutters">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="text-right">
                                        <button :disabled="(validationMessage.email) !== ''" type="submit" id="submit" name="submit" style=" font-size: 16px; background-color: #6f5499; color: #fff; font-weight: bold" class="btn">{{ buttonText }}</button>
                                    </div>
                                </div>
                            </div>   
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
           
        </div>`
})
new Vue({ el: "#forgot_password"})