using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 高新技术资料文件
    /// </summary>
    public class highTech_file : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; } 
        public int highTechId { get; set; }
        /// <summary>
        /// 证明材料：[1:生产许可证,2:国内知识产权证书,3:检测报告,4:能源体系证书,5:销售发票
        ///           6:销售合同,7:用户反馈,8:产品图片,9:其他]
        /// </summary>
        public int type { get; set; }

        /// <summary>
        /// 文件路径
        /// </summary>
        [MaxLength(200)]
        public string filepath { get; set; }

            /// <summary>
        /// 文件名称
        /// </summary>
        [MaxLength(100)]
        public string fileName { get; set; }

    }
}
