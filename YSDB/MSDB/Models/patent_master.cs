using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class patent_master : tablebase
    {
        [Key]
        public int id { get; set; }

        [Required, MaxLength(50)]
        public string masterName { get; set; }
        [Required, MaxLength(50)]
        public string linkTel { get; set; }

        [MaxLength(50)]
        public string linkEmail { get; set; }

        [MaxLength(200)]
        public string address { get; set; }
        ///<summary>
        /// 流程负责人,关联系统用户
        ///</summary>
        public int userId { get; set; }
        [MaxLength(200)]
        public string remark { get; set; }

    }
}