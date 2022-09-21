using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
  public class sys_document : tablebase
  {
    [Key]
    public int id { get; set; }
    /// <summary>
    /// 文件名
    /// </summary>
    [Required, MaxLength(100)]
    public string fileName { get; set; }
    /// <summary>
    /// 文件路径
    /// </summary>
    [Required, MaxLength(200)]
    public string filePath { get; set; }
    /// <summary>
    /// 文件类型，0:人员制度，1：财务制度，2：研发项目管理办法
    /// </summary>
    public int fileType { get; set; }
    /// <summary>
    /// 文件清单Id,对应表sys_docList的Id
    /// </summary>
    public int listId { get; set; }
    /// <summary>
    /// 下载次数
    /// </summary>
    public int downloadTimes { get; set; }

    public int companyId { get; set; }

    /// <summary>
    /// 项目Id
    /// </summary>
    public int projectId { get; set; }
    /// <summary>
    /// 字典表的类型
    /// </summary>
    public byte projectType { get; set; }

    /// <summary>
    /// 备注
    /// </summary>
    [MaxLength(200)]
    public string remark { get; set; }
    /// <summary>
    /// 年份
    /// </summary>
    public short year { get; set; }
    /// <summary>
    /// 月份
    /// </summary>
    public short month { get; set; }
    /// <summary>
    /// 文档月份
    /// </summary>
    public DateTime? docMonth { get; set; }

    /// <summary>
    /// 专利号
    /// </summary>
    [MaxLength(50)]
    public string patentNo { get; set; }

    /// <summary>
    /// 针对生产日报表表示生产日期
    /// </summary>
    public DateTime? docDate { get; set; }
    /// <summary>
    /// 工艺线id
    /// </summary>
    public int? workshopId { get; set; }

    /// <summary>
    /// 车间
    /// </summary>
    [MaxLength(100)]
    public string workshop { get; set; }
    /// <summary>
    /// 项目阶段
    /// </summary>
    [MaxLength(50)]
    public string stageKey { get; set; }

  ///<summary>
  /// 机构建设文件类型:0:制度文件，1：佐证材料
  ///</summary>
    public int? buildType{get;set;} 
  }
}
