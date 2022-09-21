using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_docFile_attachment : tablebase
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }

        /// <summary>
        /// 项目ID

        /// </summary>
        public int projectId { get; set; }

        /// <summary>
        /// 文档id

        /// </summary>
        public int? docFileId { get; set; }

        /// <summary>
        /// 文件名称
        /// </summary>
        [Required, MaxLength(200)]
        public string fileName { get; set; }

        /// <summary>
        /// 文件路径
        /// </summary>
        [Required, MaxLength(200)]
        public string filePath { get; set; }

        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime? uploadTime { get; set; }

        ///<summary>
        /// 附件类型 1.会议纪要，2.实验记录，3.试制排期，4.试制报表，5.试制报告
        ///6.技术培训，7.物料清单（BOM），8.作业指导书（SOP），9.发明专利，10.实用新型专利，11.外观设计专利，12.计算机软件著作权，13.集成电路布图设计
        ///14.科技查新报告，15.论文期刊，16.国家标准，17.行业标准，18.地方标准，19.团体标准，20.企业标准，21.技术原理
        ///22.技术图纸，23.技术方案，24.技术规范，25.工艺规程，26检测报告，27.样品样机，99其他
        ///</summary>
        public int fileType { get; set; }
    }
}
