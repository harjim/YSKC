using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    ///<summary>
    /// 表单提交类型
    ///</summary>
    public class flowInstance_form : tablebase
    {
        [Key]
        public int id { get; set; }

        public int formId { get; set; }

        public int? instanceId { get; set; }
 
        public int moduleId { get; set; }

        public int status{ get; set; }
    }
}
