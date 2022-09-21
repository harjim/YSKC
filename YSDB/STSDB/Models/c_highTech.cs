using System;
using System.ComponentModel.DataAnnotations;

namespace STSDB.Models
{
    public class c_highTech : tablebase
    {
        public int id { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 高新技术产品名称
        /// </summary>
        [MaxLength(200), Required]
        public string hpName { get; set; }

        /// <summary>
        /// 技术领域
        /// </summary>
        [MaxLength(20), Required]
        public string tecIndustry { get; set; }
        
        public int year { get; set; }
    }
}
