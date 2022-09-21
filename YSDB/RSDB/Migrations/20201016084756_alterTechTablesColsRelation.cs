using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterTechTablesColsRelation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "appGuideUrl",
                table: "t_product");

            migrationBuilder.DropColumn(
                name: "hasStage",
                table: "t_product");

            migrationBuilder.DropColumn(
                name: "noticeUrl",
                table: "t_product");

            migrationBuilder.RenameColumn(
                name: "productId",
                table: "t_product_stage_list",
                newName: "directionId");

            migrationBuilder.RenameColumn(
                name: "productId",
                table: "t_product_stage",
                newName: "seq");

            migrationBuilder.RenameColumn(
                name: "publicItemUrl",
                table: "t_product",
                newName: "address");

            migrationBuilder.RenameColumn(
                name: "noticeNo",
                table: "t_product",
                newName: "govName");

            migrationBuilder.AddColumn<int>(
                name: "directionId",
                table: "t_product_stage",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "expiryDate",
                table: "t_product_stage",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AlterColumn<string>(
                name: "pLevel",
                table: "t_product",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<string>(
                name: "direction",
                table: "t_direction",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 20);

            migrationBuilder.AddColumn<string>(
                name: "appGuideUrl",
                table: "t_direction",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "hasStage",
                table: "t_direction",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "linkName",
                table: "t_direction",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "linkTel",
                table: "t_direction",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "mainDirection",
                table: "t_direction",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "noticeNo",
                table: "t_direction",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "noticeUrl",
                table: "t_direction",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "policyPath",
                table: "t_direction",
                maxLength: 300,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "publicItemUrl",
                table: "t_direction",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "directionId",
                table: "t_product_stage");

            migrationBuilder.DropColumn(
                name: "expiryDate",
                table: "t_product_stage");

            migrationBuilder.DropColumn(
                name: "appGuideUrl",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "hasStage",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "linkName",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "linkTel",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "mainDirection",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "noticeNo",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "noticeUrl",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "policyPath",
                table: "t_direction");

            migrationBuilder.DropColumn(
                name: "publicItemUrl",
                table: "t_direction");

            migrationBuilder.RenameColumn(
                name: "directionId",
                table: "t_product_stage_list",
                newName: "productId");

            migrationBuilder.RenameColumn(
                name: "seq",
                table: "t_product_stage",
                newName: "productId");

            migrationBuilder.RenameColumn(
                name: "govName",
                table: "t_product",
                newName: "noticeNo");

            migrationBuilder.RenameColumn(
                name: "address",
                table: "t_product",
                newName: "publicItemUrl");

            migrationBuilder.AlterColumn<int>(
                name: "pLevel",
                table: "t_product",
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50);

            migrationBuilder.AddColumn<string>(
                name: "appGuideUrl",
                table: "t_product",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "hasStage",
                table: "t_product",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "noticeUrl",
                table: "t_product",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "direction",
                table: "t_direction",
                maxLength: 20,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50);
        }
    }
}
