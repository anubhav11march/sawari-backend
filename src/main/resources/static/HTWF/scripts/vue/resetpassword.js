Vue.component('resetPassword',{
    data: function (){
        return{
            password:'',
            cpassword:'',
            validationMessage: [],
        }
    },
    watch: {

        password(value) {
            this.password = value
            this.minimumLength(value)
            this.mustContainLowerCase(value)
            this.mustContainNumber(value)
            this.mustContainSpecialCharacter(value)
            this.mustContainUppercase(value)
        },
        cpassword(value){
          this.cpassword = value
          this.confirmPassword(value)
        },

    },
    methods:{
        confirmPassword(value){
          if(value!=this.password){
              this.validationMessage.confirmPassword = `${i18next.t('passwordConfirmation')}`
          }else{
              this.validationMessage.confirmPassword = '';
          }
        },
        minimumLength(value) {
            let difference = 8 - value.length;
            if (value.length > 0 && value.length < 8) {
                this.validationMessage.minimumLength = 'Must be 8 characters! '+ difference + ' characters left' ;
            } else {
                this.validationMessage.minimumLength = '';
            }
        },
        mustContainUppercase(value) {
            if (/[A-Z]/.test(value)) {
                this.validationMessage.upperCase = ''
            } else if (value === '') {
                this.validationMessage.upperCase = ''
            } else {
                this.validationMessage.upperCase = `${i18next.t('upperCase')}`
            }
        },
        mustContainLowerCase(value) {
            if (/[a-z]/.test(value)) {
                this.validationMessage.lowerCase = ''
            } else if (value === '') {
                this.validationMessage.lowerCase = ''
            } else {
                this.validationMessage.lowerCase = `${i18next.t('lowerCase')}`
            }
        },
        mustContainNumber(value) {
            if (/[0-9]/.test(value)) {
                this.validationMessage.number = ''
            } else if (value === '') {
                this.validationMessage.number = ''
            } else {
                this.validationMessage.number = `${i18next.t('oneDigit')}`
            }
        },
        mustContainSpecialCharacter(value) {
            if (/[#?!@$%^&*-]/.test(value)) {
                this.validationMessage.specialCharacter = ''
            } else if (value === '') {
                this.validationMessage.specialCharacter = ''
            } else {
                this.validationMessage.specialCharacter = `${i18next.t('oneSpecialCharacter')}`
            }
        },
        resetPassword(){
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            var self = this;
            var data = {password: this.password,}
            let urlParams = new URLSearchParams(window.location.search);
            const request = {
                method: 'POST',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: data,
                url: '/init/reset-password?reqType='+urlParams.get('reqType')+'&token='+urlParams.get('token'),
            }
            axios(request)
                .then((response)=> {
                    swal("Success!", "Password Succesfully Changed!", "success");
                    this.password='';
                    setTimeout(function(){
                        window.location.href = "/login"

                    }, 3000);
                })
                .catch((error)=> {
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
                        <form @submit.prevent="resetPassword">
                            <div class="row gutters">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="password">${i18next.t('password')}</label>
                                        <input type="password" class="form-control" id="password" v-model="password" placeholder="${i18next.t('password')}" required>  
                                    </div>
                                    <ul class="text-left">
                                    <span class="text text-danger text-sm" v-if="validationMessage.minimumLength"><li>{{ validationMessage.minimumLength }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.lowerCase"><li>{{ validationMessage.lowerCase }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.upperCase"><li>{{ validationMessage.upperCase }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.number"><li>{{ validationMessage.number }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.specialCharacter"><li>{{ validationMessage.specialCharacter }}</li></span>
                                    </ul>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="password">${i18next.t('confirmPassword')}</label>
                                        <input type="password" class="form-control"  v-model="cpassword"  placeholder="${i18next.t('confirmPassword')}" required>  
                                    </div>
                                    <ul class="text-left">
                                    <span class="text text-danger text-sm" v-if="validationMessage.confirmPassword">{{ validationMessage.confirmPassword }}</span>
                                    </ul>
                                </div>
                            </div> 
                            <div class="row gutters">
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <div class="text-right">
                                        <button :disabled="(validationMessage.minimumLength || validationMessage.lowerCase || validationMessage.upperCase || validationMessage.number || validationMessage.specialCharacter || validationMessage.confirmPassword) !== '' " type="submit" id="submit" name="submit" style=" font-size: 16px; background-color: #6f5499; color: #fff; font-weight: bold" class="btn">${i18next.t('resetPassword')}</button>
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
new Vue({el: '#reset-password'})
