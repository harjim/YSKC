using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createService_applyAndWork_recordTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "flowInstance_form",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    formId = table.Column<int>(nullable: false),
                    isntanceId = table.Column<int>(nullable: true),
                    moduleId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_form", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "service_apply",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    serviceNo = table.Column<string>(maxLength: 20, nullable: false),
                    ownerId = table.Column<int>(nullable: false),
                    begin = table.Column<DateTime>(nullable: false),
                    end = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    customers = table.Column<string>(maxLength: 200, nullable: false),
                    techManagerId = table.Column<int>(nullable: true),
                    techDirectorId = table.Column<int>(nullable: true),
                    finaManagerId = table.Column<int>(nullable: true),
                    finaDirectorId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_service_apply", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "service_apply_customer",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    serviceApplyId = table.Column<int>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    causes = table.Column<string>(maxLength: 200, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_service_apply_customer", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "service_apply_member",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    serviceApplyId = table.Column<int>(nullable: false),
                    mtype = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_service_apply_member", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "work_record",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    serviceNo = table.Column<string>(maxLength: 20, nullable: true),
                    ownerId = table.Column<int>(nullable: false),
                    itemCnt = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_work_record", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "work_record_item",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    workRecordId = table.Column<int>(nullable: false),
                    itemType = table.Column<string>(maxLength: 10, nullable: true),
                    begin = table.Column<DateTime>(nullable: false),
                    end = table.Column<DateTime>(nullable: false),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_work_record_item", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_form_moduleId_formId",
                table: "flowInstance_form",
                columns: new[] { "moduleId", "formId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowInstance_form");

            migrationBuilder.DropTable(
                name: "service_apply");

            migrationBuilder.DropTable(
                name: "service_apply_customer");

            migrationBuilder.DropTable(
                name: "service_apply_member");

            migrationBuilder.DropTable(
                name: "work_record");

            migrationBuilder.DropTable(
                name: "work_record_item");
        }
    }
}
