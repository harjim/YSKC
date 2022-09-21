using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_docFile_attachmentUploadTimeAndDocFileIdCanBeNull : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "uploadTime",
                table: "p_docFile_attachment",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AlterColumn<int>(
                name: "docFileId",
                table: "p_docFile_attachment",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "uploadTime",
                table: "p_docFile_attachment",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "docFileId",
                table: "p_docFile_attachment",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
