import Vue from "vue";
import TrDatePicker from "@/components/trDatePicker";
import TrEmail from "@/components/trEmail";
import TrInput from "@/components/trInput";
import TrInputNumber from "@/components/trInputNumber";
import TrSelect from "@/components/trSelect";
import TrTelFax from "@/components/trTelFax";
import TrZipCode from "@/components/trZipCode";

// register globally
Vue.component("tr-date-picker", TrDatePicker);
Vue.component("tr-email", TrEmail);
Vue.component("tr-input", TrInput);
Vue.component("tr-input-number", TrInputNumber);
Vue.component("tr-select", TrSelect);
Vue.component("tr-tel-fax", TrTelFax);
Vue.component("tr-zip-code", TrZipCode);
