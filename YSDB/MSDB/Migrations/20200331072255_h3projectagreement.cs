using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class h3projectagreement : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "h3_agreement",
                columns: table => new
                {
                    objectId = table.Column<string>(maxLength: 50, nullable: false),
                    Client = table.Column<string>(maxLength: 80, nullable: true),
                    clientname = table.Column<string>(maxLength: 100, nullable: true),
                    SeqNo = table.Column<string>(maxLength: 50, nullable: true),
                    OwnerId = table.Column<string>(maxLength: 30, nullable: true),
                    OwnerDeptId = table.Column<string>(maxLength: 100, nullable: true),
                    Approvers = table.Column<string>(maxLength: 30, nullable: true),
                    ConStatus = table.Column<string>(maxLength: 20, nullable: true),
                    F0000022 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000021 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000016 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000018 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000017 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000019 = table.Column<string>(maxLength: 30, nullable: true),
                    CreatedTime = table.Column<DateTime>(nullable: false),
                    ModifiedTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_h3_agreement", x => x.objectId);
                });

            migrationBuilder.CreateTable(
                name: "h3_project",
                columns: table => new
                {
                    objectId = table.Column<string>(maxLength: 50, nullable: false),
                    F0000006 = table.Column<string>(maxLength: 100, nullable: true),
                    F0000534 = table.Column<string>(maxLength: 100, nullable: true),
                    ProjectYear = table.Column<int>(nullable: false),
                    Sales = table.Column<string>(maxLength: 30, nullable: true),
                    OwnerDeptId = table.Column<string>(maxLength: 100, nullable: true),
                    F0000523 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000524 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000111 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000112 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000114 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000115 = table.Column<string>(maxLength: 30, nullable: true),
                    F0000537 = table.Column<string>(maxLength: 20, nullable: true),
                    F0000538 = table.Column<string>(maxLength: 20, nullable: true),
                    F0000539 = table.Column<string>(maxLength: 20, nullable: true),
                    F0000540 = table.Column<string>(maxLength: 20, nullable: true),
                    F0000541 = table.Column<string>(maxLength: 20, nullable: true),
                    F0000542 = table.Column<string>(maxLength: 20, nullable: true),
                    CreatedTime = table.Column<DateTime>(nullable: false),
                    ModifiedTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_h3_project", x => x.objectId);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "h3_agreement");

            migrationBuilder.DropTable(
                name: "h3_project");
        }
    }
}
