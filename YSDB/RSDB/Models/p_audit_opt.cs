using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    public class p_audit_opt:tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int projectId { get; set; }

        public int year { get; set; }
        ///<summary>
        ///工厂技术
        ///</summary>
        [MaxLength(30)]
        public string ftyTech { get; set; }
        ///<summary>
        ///工厂财务
        ///</summary>
        [MaxLength(30)]
        public string ftyFina { get; set; }

        ///<summary>
        ///区域技术
        ///</summary>
        [MaxLength(30)]
        public string areaTech { get; set; }
        ///<summary>
        ///区域财务
        ///</summary>
        [MaxLength(30)]
        public string areaFina { get; set; }
    }
}
