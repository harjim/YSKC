using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_patent_opinion : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        public int patentPlanId { get; set; }
        /// <summary>
        /// 当次意见对应的文件
        /// </summary>
        [MaxLength(1000)]
        public string filepath { get; set; }
        /// <summary>
        /// 意见
        /// </summary>
        [MaxLength(2000)]
        public string opinion { get; set; }
        


    }
}